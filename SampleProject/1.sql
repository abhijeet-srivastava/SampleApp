SELECT ra.answ_text, ra.survey_stub, ra.qstn_stub, ra.respondent_stub, case answ_tag_id when 1 then 'Other: ' when 4 then '[NA]' when 2 then 'Comments' else ra.answ_text end answer_text, ISNULL(ra.inner_qstn_text, '') AS category
                                                                                  FROM dbo. RESPONDENT_ANSWER_DETAIL ra WITH (NOLOCK)
                                                                                    INNER JOIN (
                                                                                                 SELECT ira.acct_id, ira.qstn_stub, ira.respondent_stub, ira.survey_stub
                                                                                                 FROM dbo. RESPONDENT_ANSWER_DETAIL ira WITH (NOLOCK)
                                                                                                 WHERE (ira.acct_id = 5 AND ira.survey_stub = 'F6574838-69BB-4D74-87C8-0BEE272957A9' AND ira.qstn_stub not in (SELECT qstn_stub from LU_RESPONDENT_EVENT_QUESTION))
                                                                                               ) isql
                                                                                      ON
                                                                                        isql.acct_id = ra.acct_id
                                                                                        AND isql.survey_stub = ra.survey_stub
                                                                                        AND isql.qstn_stub = ra.qstn_stub
                                                                                        AND isql.respondent_stub = ra.respondent_stub

                                                                                  WHERE (ra.acct_id = 5 AND ra.survey_stub = 'F6574838-69BB-4D74-87C8-0BEE272957A9')


SELECT ra.answ_text, ra.survey_stub, ra.qstn_stub, ra.respondent_stub, case answ_tag_id when 1 then 'Other: ' when 4 then '[NA]' when 2 then 'Comments' else ra.answ_text end answer_text, ISNULL(ra.inner_qstn_text, '') AS category
FROM dbo. RESPONDENT_ANSWER_DETAIL ra WITH (NOLOCK)
  INNER JOIN (
               SELECT ira.acct_id, ira.qstn_stub, ira.respondent_stub, ira.survey_stub
               FROM dbo. RESPONDENT_ANSWER ira WITH (NOLOCK)
               WHERE (ira.acct_id = 4 AND ira.survey_stub = 'F6574838-69BB-4D74-87C8-0BEE272957A9' AND ira.qstn_stub not in (SELECT qstn_stub from LU_RESPONDENT_EVENT_QUESTION))
             ) isql
    ON
      isql.acct_id = ra.acct_id
      AND isql.survey_stub = ra.survey_stub
      AND isql.qstn_stub = ra.qstn_stub
      AND isql.respondent_stub = ra.respondent_stub

WHERE (ra.acct_id = 4 AND ra.survey_stub = 'F6574838-69BB-4D74-87C8-0BEE272957A9')