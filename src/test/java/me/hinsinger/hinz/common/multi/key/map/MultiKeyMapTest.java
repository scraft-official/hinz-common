package me.hinsinger.hinz.common.multi.key.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import me.hinsinger.hinz.common.multi.key.map.impl.BiMultiKeyMap;
import me.hinsinger.hinz.common.multi.key.map.impl.TetraMultiKeyMap;
import me.hinsinger.hinz.common.multi.key.map.impl.TriMultiKeyMap;
import me.hinsinger.hinz.common.multi.key.wildcard.impl.MultiKeyWildcards;

public class MultiKeyMapTest {

    private BiMultiKeyMap<String, String, Integer> bi;
    private TriMultiKeyMap<String, String, String, Integer> tri;
    private TetraMultiKeyMap<String, String, String, String, Integer> tetra;

    @BeforeEach
    public void setup() {
        bi = MultiKeyMap.<String, String, Integer>bi();
        tri = MultiKeyMap.<String, String, String, Integer>tri();
        tetra = MultiKeyMap.<String, String, String, String, Integer>tetra();
    }

    // ---------- BiMultiKeyMap ----------

    @Test
    public void biPutGetExactAndOverwrite() {
        bi.put("A", "B", 1);
        assertEquals(Integer.valueOf(1), bi.getValue("A", "B").orElse(null));
        bi.put("A", "B", 2); // overwrite
        assertEquals(Integer.valueOf(2), bi.getValue("A", "B").orElse(null));
    }

    @Test
    public void biWildcardBehavior() {
        bi.put("A", "B", 1);
        bi.put("A", "C", 2);
        bi.put("X", "B", 3);

        // wildcard second key
        Collection<Integer> allWithA = bi.getValues("A", MultiKeyWildcards.ANY);
        assertEquals(setOf(1, 2), new HashSet<>(allWithA));

        // wildcard first key
        Collection<Integer> allWithB = bi.getValues(MultiKeyWildcards.ANY, "B");
        assertEquals(setOf(1, 3), new HashSet<>(allWithB));

        // both wildcards
        Collection<Integer> all = bi.getValues(MultiKeyWildcards.ANY, MultiKeyWildcards.ANY);
        assertEquals(setOf(1, 2, 3), new HashSet<>(all));
    }

    @Test
    public void biKeySetsMix() {
        bi.put("A", "B", 1);
        bi.put("A", "C", 2);
        bi.put("D", "E", 3);

        // all first-level keys
        Set<String> firstLevel = bi.getKeys();
        assertEquals(setOf("A", "D"), firstLevel);

        // second-level under A
        Set<String> secondsUnderA = bi.getKeys("A");
        assertEquals(setOf("B", "C"), secondsUnderA);

        // wildcard first-level => union of all second-level
        Set<String> secondsWildcardA = bi.getKeys(MultiKeyWildcards.ANY);
        assertTrue(secondsWildcardA.containsAll(Arrays.asList("B", "C", "E")));
    }

    @Test
    public void biMissingReturnsEmpty() {
        assertFalse(bi.getValue("No", "Key").isPresent());
        assertTrue(bi.getValues("No", "Key").isEmpty());
        assertTrue(bi.getValues(MultiKeyWildcards.ANY, "Key").isEmpty());
        assertTrue(bi.getValues("No", MultiKeyWildcards.ANY).isEmpty());
    }

    @Test
    public void biNullInPutThrows() {
        assertThrowsExactly(IllegalArgumentException.class, () -> bi.put(null, "B", 1));
        assertThrowsExactly(IllegalArgumentException.class, () -> bi.put("A", null, 1));
    }

    // ---------- TriMultiKeyMap ----------

    @Test
    public void triPutGetExactWildcardCombinations() {
        tri.put("A", "B", "C", 10);
        tri.put("A", "B", "D", 20);
        tri.put("A", "X", "C", 30);
        tri.put("Z", "B", "C", 40);

        assertEquals(Integer.valueOf(10), tri.getValue("A", "B", "C").get());
        assertFalse(tri.getValue("A", "B", "Z").isPresent());

        // wildcard last
        assertEquals(setOf(10, 20), new HashSet<>(tri.getValues("A", "B", MultiKeyWildcards.ANY)));
        // wildcard first
        assertEquals(setOf(10, 40), new HashSet<>(tri.getValues(MultiKeyWildcards.ANY, "B", "C")));
        // all wildcard
        assertEquals(setOf(10,20,30,40), new HashSet<>(tri.getValues(MultiKeyWildcards.ANY, MultiKeyWildcards.ANY, MultiKeyWildcards.ANY)));
    }

    @Test
    public void triKeySetVariants() {
        tri.put("A", "B", "C", 1);
        tri.put("A", "B", "D", 2);
        tri.put("A", "X", "C", 3);
        tri.put("Z", "B", "C", 4);

        Set<String> first = tri.getKeys();
        assertEquals(setOf("A", "Z"), first);

        Set<String> secondUnderA = tri.getKeys("A");
        assertEquals(setOf("B", "X"), secondUnderA);

        Set<String> secondWildcard = tri.getKeys(MultiKeyWildcards.ANY);
        assertTrue(secondWildcard.containsAll(Arrays.asList("B", "X")));

        Set<String> thirdUnderA_B = tri.getKeys("A", "B");
        assertEquals(setOf("C", "D"), thirdUnderA_B);
    }

    // ---------- TetraMultiKeyMap ----------

    @Test
    public void tetraBasicAndWildcard() {
        tetra.put("A", "B", "C", "D", 100);
        tetra.put("A", "B", "C", "E", 200);
        tetra.put("A", "B", "X", "D", 300);
        tetra.put("Z", "B", "C", "D", 400);

        assertEquals(Integer.valueOf(100), tetra.getValue("A", "B", "C", "D").get());

        Collection<Integer> pattern1 = tetra.getValues("A", "B", "C", MultiKeyWildcards.ANY);
        assertEquals(setOf(100,200), new HashSet<>(pattern1));

        Collection<Integer> pattern2 = tetra.getValues("A", "B", MultiKeyWildcards.ANY, "D");
        assertEquals(setOf(100,300), new HashSet<>(pattern2));

        Collection<Integer> all = tetra.getValues(MultiKeyWildcards.ANY, MultiKeyWildcards.ANY, MultiKeyWildcards.ANY, MultiKeyWildcards.ANY);
        assertEquals(setOf(100,200,300,400), new HashSet<>(all));
    }

    @Test
    public void tetraKeySetDrillDown() {
        tetra.put("A", "B", "C", "D", 1);
        tetra.put("A", "B", "C", "E", 2);
        tetra.put("A", "X", "C", "D", 3);
        tetra.put("Z", "B", "C", "D", 4);

        assertEquals(setOf("A", "Z"), tetra.getKeys());
        assertEquals(setOf("B", "X"), tetra.getKeys("A"));
        assertEquals(setOf("C"), tetra.getKeys("A", "B"));
        assertEquals(setOf("D", "E"), tetra.getKeys("A", "B", "C"));
    }

    // ---------- concurrency / large dataset smoke ----------

    @Test
    public void triLargeDatasetWildcardPerformanceSmoke() {
        TriMultiKeyMap<Integer, Integer, Integer, Integer> big = MultiKeyMap.<Integer, Integer, Integer, Integer>tri();
        int outer = 500; // A
        int mid = 100; // B
        int inner = 50; // C
        int counter = 0;
        for (int a = 0; a < outer; a++) {
            for (int b = 0; b < mid; b++) {
                for (int c = 0; c < inner; c++) {
                    big.put(a, b, c, counter++);
                }
            }
        }
        long start = System.nanoTime();
        Collection<Integer> values = big.getValues(1, 2, MultiKeyWildcards.ANY);
        long duration = System.nanoTime() - start;
        assertEquals(inner, values.size());
        assertTrue(duration < TimeUnit.MILLISECONDS.toNanos(50), "Wildcard lookup too slow: " + duration);
    }

    @Test
    public void concurrentReadWriteWithExternalSync() throws InterruptedException {
        final BiMultiKeyMap<String, String, Integer> map = MultiKeyMap.<String, String, Integer>bi();
        ExecutorService exe = Executors.newFixedThreadPool(4);
        final Object lock = new Object();
        int tasks = 500;
        CountDownLatch latch = new CountDownLatch(tasks);
        for (int i = 0; i < tasks; i++) {
            final int idx = i;
            exe.submit(() -> {
                synchronized (lock) {
                    map.put("A" + (idx % 10), "B" + (idx % 5), idx);
                    Collection<Integer> vals = map.getValues("A" + (idx % 10), MultiKeyWildcards.ANY);
                    assertNotNull(vals);
                }
                latch.countDown();
            });
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        exe.shutdownNow();
        Collection<Integer> some = map.getValues("A1", MultiKeyWildcards.ANY);
        assertFalse(some.isEmpty());
    }

    // ---------- edge / negative ----------

    @Test
    public void getValuesNoMatchEmpty() {
        Collection<Integer> empty = tri.getValues("X", "Y", "Z");
        assertTrue(empty.isEmpty());
    }

    @Test
    public void invalidPutNullsThrow() {
        assertThrowsExactly(IllegalArgumentException.class, () -> bi.put(null, "B", 1));
        assertThrowsExactly(IllegalArgumentException.class, () -> tri.put("A", null, "C", 2));
        assertThrowsExactly(IllegalArgumentException.class, () -> tetra.put("A", "B", null, "D", 3));
    }

    // ---------- utility ----------

    private static <T> Set<T> setOf(T... elems) {
        return new HashSet<>(Arrays.asList(elems));
    }
}
