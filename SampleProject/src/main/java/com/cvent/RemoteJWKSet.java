package com.cvent;

import com.nimbusds.jose.RemoteKeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKMatcher;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by a.srivastava on 12/17/16.
 */
public class RemoteJWKSet<C extends SecurityContext> implements JWKSource<C> {

    /**
     * The JWK set URL.
     */
    private final URL jwkSetURL;


    /**
     * The cached JWK set.
     */
    private final AtomicReference<JWKSet> cachedJWKSet = new AtomicReference<>();



    /**
     * Creates a new remote JWK set using the
     * {@link DefaultResourceRetriever default HTTP resource retriever},
     * with a HTTP connect timeout set to 250 ms, HTTP read timeout set to
     * 250 ms and a 50 KByte size limit.
     *
     * @param jwkSetURL The JWK set URL. Must not be {@code null}.
     */
    public RemoteJWKSet(final URL jwkSetURL) {
        this.jwkSetURL = jwkSetURL;
    }

    /**
     * Updates the cached JWK set from the configured URL.
     *
     * @return The updated JWK set.
     *
     * @throws RemoteKeySourceException If JWK retrieval failed.
     */
    private JWKSet updateJWKSetFromURL()
            throws RemoteKeySourceException {
        String content;
        try {
            content = readJsonFromUrl();
        } catch (IOException e) {
            throw new RemoteKeySourceException("Couldn't parse remote JWK set: " + e.getMessage(), e);
        }
        JWKSet jwkSet;
        try {
            jwkSet = JWKSet.parse(content);
        } catch (java.text.ParseException e) {
            throw new RemoteKeySourceException("Couldn't parse remote JWK set: " + e.getMessage(), e);
        }
        cachedJWKSet.set(jwkSet);
        return jwkSet;
    }

    private  String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public  String readJsonFromUrl() throws IOException{
        InputStream is = jwkSetURL.openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return jsonText;
        } finally {
            is.close();
        }
    }

    /**
     * Returns the JWK set URL.
     *
     * @return The JWK set URL.
     */
    public URL getJWKSetURL() {
        return jwkSetURL;
    }


    /**
     * Returns the cached JWK set.
     *
     * @return The cached JWK set, {@code null} if none.
     */
    public JWKSet getCachedJWKSet() {
        return cachedJWKSet.get();
    }


    /**
     * Returns the first specified key ID (kid) for a JWK matcher.
     *
     * @param jwkMatcher The JWK matcher. Must not be {@code null}.
     *
     * @return The first key ID, {@code null} if none.
     */
    protected static String getFirstSpecifiedKeyID(final JWKMatcher jwkMatcher) {

        Set<String> keyIDs = jwkMatcher.getKeyIDs();

        if (keyIDs == null || keyIDs.isEmpty()) {
            return null;
        }

        for (String id: keyIDs) {
            if (id != null) {
                return id;
            }
        }
        return null; // No kid in matcher
    }


    /**
     * {@inheritDoc} The security context is ignored.
     */
    @Override
    public List<JWK> get(final JWKSelector jwkSelector, final C context)
            throws RemoteKeySourceException {

        // Get the JWK set, may necessitate a cache update
        JWKSet jwkSet = cachedJWKSet.get();
        if (jwkSet == null) {
            jwkSet = updateJWKSetFromURL();
        }

        // Run the selector on the JWK set
        List<JWK> matches = jwkSelector.select(jwkSet);

        if (! matches.isEmpty()) {
            // Success
            return matches;
        }

        // Refresh the JWK set if the sought key ID is not in the cached JWK set

        // Looking for JWK with specific ID?
        String soughtKeyID = getFirstSpecifiedKeyID(jwkSelector.getMatcher());
        if (soughtKeyID == null) {
            // No key ID specified, return no matches
            return Collections.emptyList();
        }

        if (jwkSet.getKeyByKeyId(soughtKeyID) != null) {
            // The key ID exists in the cached JWK set, matching
            // failed for some other reason, return no matches
            return Collections.emptyList();
        }

        // Make new HTTP GET to the JWK set URL
        jwkSet = updateJWKSetFromURL();
        if (jwkSet == null) {
            // Retrieval has failed
            return Collections.emptyList();
        }

        // Repeat select, return final result (success or no matches)
        return jwkSelector.select(jwkSet);
    }
}
