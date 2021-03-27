import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.company.onetwo33.homework6.Main;

public class MainTest {
    private Main main;

    @BeforeEach
    public void init() {
        main = new Main();
    }

    @Test
    public void testNewArr1() {
        int[] arr = new int[7];
        arr[0] = 3;
        arr[1] = 6;
        arr[2] = -1;
        arr[3] = 12;
        arr[4] = 33;
        arr[5] = 4;
        arr[6] = 5;
        int[] result = new int[1];
        result[0] = 5;
        Assertions.assertArrayEquals(result, main.newArr(arr));
    }

    @Test
    public void testNewArr2() {
        int[] arr = new int[4];
        arr[0] = 18;
        arr[1] = -3;
        arr[2] = -1;
        arr[3] = 12;
        Assertions.assertThrows(RuntimeException.class, () -> {
            main.newArr(arr);
        });
    }

    @Test
    public void testNewArr3() {
        int[] arr = new int[7];
        arr[0] = 4;
        arr[1] = 4;
        arr[2] = 4;
        arr[3] = 4;
        arr[4] = 4;
        arr[5] = 4;
        arr[6] = 4;
        Assertions.assertThrows(RuntimeException.class, () -> {
            main.newArr(arr);
        });
    }

    @Test
    public void testNewArr4() {
        int[] arr = new int[0];
        Assertions.assertThrows(RuntimeException.class, () -> {
            main.newArr(arr);
        });
    }

    @Test
    public void testFindOneOrFourInArr1() {
        int[] arr = new int[7];
        arr[0] = 3;
        arr[1] = 6;
        arr[2] = -1;
        arr[3] = 12;
        arr[4] = 33;
        arr[5] = 4;
        arr[6] = 5;
        Assertions.assertTrue(main.findOneOrFourInArr(arr));
    }

    @Test
    public void testFindOneOrFourInArr2() {
        int[] arr = new int[0];
        Assertions.assertFalse(main.findOneOrFourInArr(arr));
    }

    @Test
    public void testFindOneOrFourInArr3() {
        int[] arr = new int[7];
        arr[0] = 1;
        arr[1] = 3;
        arr[2] = 3;
        arr[3] = 3;
        arr[4] = 3;
        arr[5] = 3;
        arr[6] = 3;
        Assertions.assertTrue(main.findOneOrFourInArr(arr));
    }
}
