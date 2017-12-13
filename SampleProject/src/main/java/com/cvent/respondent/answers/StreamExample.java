package com.cvent.respondent.answers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by a.srivastava on 7/4/16.
 */
public class StreamExample {

    public static void main(String[] args) {
        
        //flatMap();
        testFrom();
       /* predicates();
        closureExample();
        streamExamples();
        streamMaps();

        streamPeeking();
        
        streamReducing();
        
        infiniteStream();
        
        streamOptional();
        
        primitiveStreams();
        
        streamWarnings();
        
        predicates();*/
    }

    private static void testFrom() {
        boolean isInner = false;
        String[] array = {"answer_text","survey_stub","respondent_stub","qstn_stub","answer_score, score_detail","answer_detail"};
        Stream.of(array)
            .filter(e -> !("answer_text".equalsIgnoreCase(e) && isInner))    
        .forEach(System.out::println);           
    }

    private static void flatMap() {
        String[] array = {"a", "b", "c", "d,f,g,h,i", "e"};

        //Arrays.stream
        Arrays.stream(array)
                .flatMap(e -> Stream.of(e.split(",")))
                .forEach(System.out::println);
        //stream1.forEach(x -> System.out.println(x));
        
    }

    public static void print(Integer num , Predicate<Integer> predicate, String msg) {
        System.out.println(num + " " + msg + " : " + predicate.test(num));
    }
    private static void predicates() {
        Predicate<Integer> isEven = e -> (e & 1) == 0;
        Predicate<Integer> isGT10 = e -> e > 10;
        print(6, isEven, "? isEven");
        print(8, isEven.and(isGT10), "? isEven");
    }

    private static void streamWarnings() {

        Stream<String> stream = Stream.of("Mario", "Luigi", "Yoshi", "Toad");
        String[] result = stream.toArray(String[]::new);
// stream.toArray() RETURNS AN ARRAY OF TYPE Object[]
        List<String> characters = new ArrayList<String>();
        characters.addAll(Arrays.asList("Mario", "Luigi", "Yoshi", "Toad"));
        Stream<String> stream1 = characters.stream().filter(e -> e.startsWith("M"));
        characters.add("Magikoopa"); // OK, INTERMEDIATE OPERATIONS ARE LAZY
        System.out.println(stream1.collect(Collectors.toList()));
// [Mario, Magikoopa] 
        Stream<String> stream2 = characters.stream();
        stream2.forEach(e -> {if (e.startsWith("M")) characters.remove(e);}); // ERROR, INTERFERENCE 
// java.util.ConcurrentModificationException
        List<Integer> integers = IntStream.range(0, 10).boxed().collect(Collectors.toCollection(ArrayList::new));
        System.out.println(integers);
// [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] 
        System.out.println(integers.stream().peek(integers::remove).map(Object::toString).collect(Collectors.joining(", ")));
// java.lang.NullPointerException
        List<Integer> integers1 = IntStream.range(0, 10).boxed().collect(Collectors.toCollection(ArrayList::new));//IntStream.range(0, 10).boxed().collect(Collectors.toList());
        System.out.println(integers1);
// [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] 
// THE sorted OPERATION IS A "STATEFUL INTERMEDIATE OPERATION" 
        System.out.println(integers1.stream().sorted().peek(integers::remove).map(Object::toString).collect(Collectors.joining(", ")));
// 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 
        System.out.println(integers1);
// []
        List<Integer> integers2 = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        System.out.println(integers2);
// [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] 
        System.out.println(integers2.stream().parallel().sorted().peek(integers::remove).map(Object::toString).collect(Collectors.joining(", ")));
// 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
        System.out.println(integers2); // BAZINGA! 
// [8]
        Stream<Integer> stream5 = Stream.of(1, 2, 3, 5, 7);
        System.out.println(stream5.map(Object::toString).collect(Collectors.joining(", ")));
// 1, 2, 3, 5, 7 
        String[] stringArray = {"A", "B", "C"};
        Stream<String> stream3 = Stream.of(stringArray);
        System.out.println(stream3.map(Object::toString).collect(Collectors.joining(", ")));
// A, B, C 
        int[] intArray = {1, 2, 3, 5, 7};
        Stream<int[]> stream4 = Stream.of(intArray); // USE Arrays.stream(intArray)
        System.out.println(stream4.map(Object::toString).collect(Collectors.joining(", ")));
    }

    private static void primitiveStreams() {
        IntStream primes = IntStream.of(1, 2, 3, 5, 7);
        System.out.println(Arrays.toString(primes.toArray()));
// [1, 2, 3, 5, 7]
        int[] values = {1, 2, 3, 5, 7};
        IntStream primes1 = Arrays.stream(values, 0, 3);
        System.out.println(Arrays.toString(primes1.toArray()));
// [1, 2, 3] 
// UPPER BOUND IS EXCLUDED
        IntStream zeroToNine = IntStream.range(0, 10);
        zeroToNine.forEach(n -> System.out.print(n + " "));
// 0 1 2 3 4 5 6 7 8 9 
// UPPER BOUND IS INCLUDED
        IntStream zeroToTen = IntStream.rangeClosed(0, 10);
        zeroToTen.forEach(n -> System.out.print(n + " "));

        IntStream primes2 = IntStream.of(1, 2, 3, 5, 7);
// STREAM OF PRIMITIVE TYPES TO STREAM OF OBJECTS
        Stream<Integer> boxedPrimes = primes2.boxed();
// STREAM OF OBJECTS TO STREAM OF PRIMITIVE TYPES (USE mapToInt, mapToLong, mapToDouble)
        IntStream unboxedPrimes = boxedPrimes.mapToInt(Integer::intValue);
// STRING TO STREAM OF UNICODE CODE POINTS
        IntStream codes = "A B C".codePoints();
        System.out.println(Arrays.toString(codes.toArray()));
// [65, 32, 66, 32, 67] 
        Stream<String> stream = Stream.of("Mario", "Luigi", "Yoshi", "Toad");
        IntStream lengths = stream.mapToInt(String::length);
        System.out.println(Arrays.toString(lengths.toArray()));
    }

    private static void streamOptional() {
        List<String> characters = Arrays.asList("Mario", "Luigi", "Yoshi", "Toad");
        Optional<String> nameStartingWithL = characters.stream().filter(e -> e.startsWith("L")).findFirst();
        nameStartingWithL.ifPresent(System.out::println);
// Luigi
// WHENEVER YOU USE findAny YOU BETTER USE parallel
        Optional<String> nameStartingWithY = characters.stream().parallel().filter(e -> e.startsWith("Y")).findAny();
        nameStartingWithY.ifPresent(System.out::println);
// Yoshi 
        String nameStartingWithX = characters.stream().parallel().filter(e -> e.startsWith("X")).findAny().orElse("Oops...");
        System.out.println(nameStartingWithX);
// Oops...
// NO NEED TO USE filter
        boolean hasNameStartingWithM = characters.stream().parallel().anyMatch(e -> e.startsWith("M"));
        System.out.println(hasNameStartingWithM);
    }

    private static void infiniteStream() {
        //Stream<BigInteger> integers = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE));
        //System.out.println(integers.map(BigInteger::toString).collect(Collectors.joining(" ")));
// java.lang.OutOfMemoryError: Java heap space
// INFINITE SEQUENCE, VALUES WILL BE GENERATED ON THE FLY
        Stream<BigInteger> integers1 = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE));
        System.out.println(integers1.limit(10).map(BigInteger::toString).collect(Collectors.joining(" ")));
    }

    private static void streamReducing() {

        Optional<Integer> sum = Stream.of(1, 2, 3, 4).reduce((x, y) -> x + y);
// e1 + e2 + e3 + ...
        System.out.println(sum.orElse(0));
// 10
        Integer sum1 = Stream.of(1, 2, 3, 4).reduce(0, (x, y) -> x + y);
// 0 + e1 + e2 + e3 + ...
        System.out.println(sum1);
// 10
        Integer product = Stream.of(1, 2, 3, 4).reduce(1, (x, y) -> x * y);
// 1 * e1 * e2 * e3 * ...
        System.out.println(product);

        List<City> cities = createCityList();
        reducingProperty1(cities);
// 19040439 
        reducingProperties2(cities);

        streamParallelizing(cities);
    }

    private static void streamParallelizing(List<City> cities) {
        Set<String> statesWithLargestCities =
                cities.stream()
                        .parallel().filter(e -> e.getPopulation() > 1000000) // FILTERING WILL BE PERFORMED CONCURRENTLY
                        .sequential().map(City::getState).collect(Collectors.toSet()); // MAPPING WILL BE PERFORMED SEQUENTIALLY
        System.out.println(statesWithLargestCities);
    }

    private static void reducingProperties2(List<City> cities) {
        long population = cities.stream().mapToLong(City::getPopulation).sum();
        System.out.println(population);
    }

    private static void reducingProperty1(List<City> cities) {
        
        long population = cities.stream().parallel().reduce(
                0L,
                (total, city) -> total + city.getPopulation(), // ACCUMULATOR
                (total1, total2) -> total1 + total2); // COMBINER FOR PARALLEL OPERATIONS
        System.out.println(population);
    }

    private static void streamPeeking() {
        Stream<String> stream = Stream.of("Mario", "Luigi", "Yoshi", "Toad");
        stream.forEach(e -> System.out.print(e.toLowerCase() + " "));
// mario luigi yoshi toad 
        stream = Stream.of("Mario", "Luigi", "Yoshi", "Toad");
        stream.forEach(e -> System.out.print(e.toUpperCase() + " "));
// java.lang.IllegalStateException: stream has already been operated upon or closed 
        List<String> characters = Arrays.asList("Mario", "Luigi", "Yoshi", "Toad");
        characters
                .stream()
                .peek(e -> System.out.print(e.toLowerCase() + " "))
                .peek(e -> System.out.print(e.toUpperCase() + " "))
                .forEach(e -> System.out.print("(" + e + ") "));
// mario MARIO (Mario) luigi LUIGI (Luigi) yoshi YOSHI (Yoshi) toad TOAD (Toad) 
        characters
                .stream()
                .filter(e -> e.length() > 4)
                .peek(e -> System.out.print("f(" + e + ") "))
                .map(String::toUpperCase)
                .peek(e -> System.out.print("m(" + e + ") "))
                .collect(Collectors.toList());
    }

    private static void streamMaps() {
        List<City> cities = createCityList();


        System.out.println(cities.stream().map(Object::toString).collect(Collectors.joining(", ")));
// Sao Paulo/SP, Rio de Janeiro/RJ, Campinas/SP, Uberaba/MG 
        Map<Long, String> nameById = cities.stream().collect(Collectors.toMap(City::getId, City::getName));
        System.out.println(nameById);
// {1=Sao Paulo, 2=Rio de Janeiro, 3=Campinas, 4=Uberaba} 
        Map<Long, City> cityByIdMap = cities.stream().collect(Collectors.toMap(City::getId, Function.identity()));
        System.out.println(cityByIdMap);

        TreeMap<Long, City> cityByIdTreeMap = cities.stream().collect(Collectors.toMap(City::getId, Function.identity(), (v1, v2) -> v1, TreeMap::new));
        System.out.println(cityByIdTreeMap);
// {1=Sao Paulo/SP, 2=Rio de Janeiro/RJ, 3=Campinas/SP, 4=Uberaba/MG} 
        Map<String, List<City>> listOfCitiesByState = cities.stream().collect(Collectors.groupingBy(City::getState));
        System.out.println(listOfCitiesByState);
// {RJ=[Rio de Janeiro/RJ], MG=[Uberaba/MG], SP=[Sao Paulo/SP, Campinas/SP]} 
        Map<String, Set<City>> setOfCitiesByState = cities.stream().collect(Collectors.groupingBy(City::getState, Collectors.toSet()));
        System.out.println(setOfCitiesByState);
// {RJ=[Rio de Janeiro/RJ], MG=[Uberaba/MG], SP=[Campinas/SP, Sao Paulo/SP]} 
        Map<Boolean, List<City>> spCitiesAndOtherCities = cities.stream().collect(Collectors.partitioningBy(e -> e.getState().equals("SP")));
        System.out.println(spCitiesAndOtherCities);
// {false=[Rio de Janeiro/RJ, Uberaba/MG], true=[Sao Paulo/SP, Campinas/SP]} 
        List<City> spCities = spCitiesAndOtherCities.get(true);
        System.out.println(spCities);
// [Sao Paulo/SP, Campinas/SP] 
        List<City> otherCities = spCitiesAndOtherCities.get(false);
        System.out.println(otherCities);


        Map<String, Long> cityCountByState = cities.stream().collect(Collectors.groupingBy(City::getState, Collectors.counting()));
        System.out.println(cityCountByState);
// {RJ=1, MG=1, SP=2} 
        Map<String, Long> populationByState = cities.stream().collect(Collectors.groupingBy(City::getState, Collectors.summingLong(City::getPopulation)));
        System.out.println(populationByState);
// {RJ=6323037, MG=302623, SP=12414779} 
        Map<String, Optional<City>> largestCityByState = cities.stream().collect(Collectors.groupingBy(City::getState, Collectors.maxBy(Comparator.comparing(City::getPopulation))));
        System.out.println(largestCityByState);
// {RJ=Optional[Rio de Janeiro/RJ], MG=Optional[Uberaba/MG], SP=Optional[Sao Paulo/SP]}
        Map<String, Optional<City>> smallestCityByState = cities.stream().collect(Collectors.groupingBy(City::getState, Collectors.minBy(Comparator.comparing(City::getPopulation))));
        System.out.println(smallestCityByState);
// {RJ=Optional[Rio de Janeiro/RJ], MG=Optional[Uberaba/MG], SP=Optional[Campinas/SP]} 
        Map<String, Optional<String>> longestCityNameByState = cities.stream().collect(Collectors.groupingBy(City::getState, Collectors.mapping(City::getName, Collectors.maxBy(Comparator.comparing(String::length)))));
        System.out.println(longestCityNameByState);
// {RJ=Optional[Rio de Janeiro], MG=Optional[Uberaba], SP=Optional[Sao Paulo]} 
        Map<String, Set<String>> setOfCityNamesByState = cities.stream().collect(Collectors.groupingBy(City::getState, Collectors.mapping(City::getName, Collectors.toSet())));
        System.out.println(setOfCityNamesByState);
// {RJ=[Rio de Janeiro], MG=[Uberaba], SP=[Sao Paulo, Campinas]} 
        Map<String, String> cityNamesByState = cities.stream().collect(Collectors.groupingBy(City::getState, Collectors.mapping(City::getName, Collectors.joining(", "))));
        System.out.println(cityNamesByState);
// {RJ=Rio de Janeiro, MG=Uberaba, SP=Sao Paulo, Campinas} 
        Map<String, LongSummaryStatistics> populationSummaryByState = cities.stream().collect(Collectors.groupingBy(City::getState, Collectors.summarizingLong(City::getPopulation)));
        System.out.println(populationSummaryByState);

        CityStatistics(cities);

        flattenedMap(cities);
    }

    private static void flattenedMap(List<City> cities) {
        Supplier<Stream<City>> supplier = () -> cities.stream();
        List<List<String>> mappedStreets = supplier.get().map(city -> city.getStreets()).collect(Collectors.toList());
        System.out.println(mappedStreets);
// [[Av. Paulista, Av. Reboucas], [Av. Brasil], [Av. Brasil], [Av. Leopoldina]] 
        Set<String> flattenedStreets = supplier.get().flatMap(city -> city.getStreets().stream()).collect(Collectors.toSet());
        System.out.println(flattenedStreets);
    }

    private static void CityStatistics(List<City> cities) {
        LongSummaryStatistics summary = cities.stream().collect(Collectors.summarizingLong(City::getPopulation));
        System.out.println(summary.getCount());
// 4
        System.out.println(summary.getSum());
// 19040439 
        System.out.println(summary.getMin());
// 302623
        System.out.println(summary.getMax());
// 11316149
        System.out.println(summary.getAverage());
    }

    private static List<City> createCityList() {
        City city1 = new City(1L, "SP", "Sao Paulo", 11316149L, Arrays.asList("Av. Paulista", "Av. Reboucas"));
        City city2 = new City(2L, "RJ", "Rio de Janeiro", 6323037L, Arrays.asList("Av. Brasil"));
        City city3 = new City(3L, "SP", "Campinas", 1098630L, Arrays.asList("Av. Brasil"));
        City city4 = new City(4L, "MG", "Uberaba", 302623L, Arrays.asList("Av. Leopoldina"));
        return Arrays.asList(city1, city2, city3, city4);
    }

    private static void closureExample() {
        final double discount = 50.0; // FREE VARIABLES MUST BE FINAL
        Function<Double, Double> blackFridayBrazil = price -> (2 * price) * (1 - (discount / 100.0));
        System.out.println(blackFridayBrazil.apply(30.0));
    }

    private static void streamExamples() {
        List<String> characters = Arrays.asList("mario", "Mario", "luigi", "Luigi", "Yoshi", "Toad", "Toad");
        System.out.println(characters);
        System.out.println(characters.stream().count());

        System.out.println(characters.stream().distinct().count());

        System.out.println(characters.stream().map(String::toLowerCase).distinct().count());

        String result1 = characters.stream().collect(Collectors.joining());
        System.out.println(result1);
        String result2 = characters.stream().collect(Collectors.joining(", "));
        System.out.println(result2);
        String result3 = characters.stream().map(Object::toString).collect(Collectors.joining(", "));
        System.out.println(result3);

        // SUPPLIER, ACCUMULATOR, COMBINER
        Set<String> result4 = characters.stream().collect(HashSet::new, HashSet::add, HashSet::addAll);
        System.out.println(result4);
// [Yoshi, Luigi, Toad, Mario] 
        Set<String> result5 = characters.stream().collect(Collectors.toSet());
        System.out.println(result5);
// [Yoshi, Luigi, Toad, Mario]
        List<String> result6 = characters.stream().collect(Collectors.toList());
        System.out.println(result6);
// [Mario, Mario, Luigi, Yoshi, Toad, Toad] 
        TreeSet<String> result7 = characters.stream().collect(Collectors.toCollection(TreeSet::new));
        System.out.println(result7);


    }
}
