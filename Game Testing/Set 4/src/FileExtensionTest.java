import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileExtensionTest
{
    @Test
    @ExtendWith(FileExtension.class)
    void test(@FileExtension.File(path = "/home/hanna/Moje/Studia/TestowanieGier/test.txt") String content)
    {
        assertEquals(content, "To jest test.");
    }
}
