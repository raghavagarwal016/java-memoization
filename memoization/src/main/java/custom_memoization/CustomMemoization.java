package custom_memoization;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class CustomMemoization {
  private static int maxCapacity = 15;

  //automatically removes the oldest entry if size increases max capacity.
  private static Map<Integer, Integer> integerMap = Collections.synchronizedMap(new LinkedHashMap<Integer, Integer>() {
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
      return size() > maxCapacity;
    }
  });

  public static void main(String[] args) {
    //input
    int input = 5;

    // add value to map
    IntStream.rangeClosed(1, 20).forEach(i -> getMemoizedDoubleValue(i));

    //output
    long start = System.currentTimeMillis();
    Integer output = getMemoizedDoubleValue(input);
    long end = System.currentTimeMillis();
    System.out.println(String.format("Not Memoized: time = %s, output = %s", end - start, output ));

    //output
    start = System.currentTimeMillis();
    output = getMemoizedDoubleValue(input);
    end = System.currentTimeMillis();
    System.out.println(String.format("Memoized: time = %s, output = %s", end - start, output ));

    //final integer map
    System.out.println("Final integer map: " + integerMap);

  }

  // check if value is already in map and return if present otherwise call doubleValue()
  private static int getMemoizedDoubleValue(int i)  {
   return integerMap.computeIfAbsent(i, CustomMemoization::doubleValue);
  }

  //suppose to  be a computationaly expensive method
  private static int doubleValue(int i) {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return i * i;
  }

}
