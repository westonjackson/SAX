package net.seninp.jmotif.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import net.seninp.util.UCRUtils;

public class TestUCRUtils {

  private static final String CBF_GOOD_FNAME = "src//resources//dataset/test_dataset_good.txt";

  private static final String CBF_BAD_FNAME = "src//resources//dataset/test_dataset_bad.txt";

  @Test
  public void testReadUCRData() {
    try {

      Map<String, List<double[]>> data = UCRUtils.readUCRData(CBF_GOOD_FNAME);
      assertEquals(data.keySet().size(), 3);

      String stats = UCRUtils.datasetStats(data, "CBF_TRAIN ");
      assertTrue(stats.contains("series length min: 128, max: 128"));

      final File temp;
      final String prefix = "temp";
      final String suffix = Long.toString(System.nanoTime());

      temp = File.createTempFile(prefix, suffix);
      UCRUtils.saveData(data, temp);

      Map<String, List<double[]>> data2 = UCRUtils.readUCRData(temp.getCanonicalPath());
      assertEquals(data2.keySet().size(), 3);

    }
    catch (NumberFormatException | IOException e) {
      fail("Exception should not be thrown");
    }
  }

  @Test
  public void testReadUCRData2() {
    try {
      @SuppressWarnings("unused")
      Map<String, List<double[]>> data = UCRUtils.readUCRData(CBF_BAD_FNAME);
      fail("should throw an exception");
    }
    catch (NumberFormatException | IOException e) {
      assert true;
    }
  }

}
