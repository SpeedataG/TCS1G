package win.reginer.gpio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 *
 * @author :Reginer in  2017/7/6 2:55.
 *         联系方式:QQ:282921012
 *         功能描述:gpio工具类
 */
public class GpioControl {
    private BufferedWriter mControlFile;
    public static final String MAIN_GPIO = "sys/class/misc/mtgpio/pin";
    public static final String OUT_GPIO = "/sys/class/misc/aw9523/gpio";

    /**
     * 初始化.
     *
     * @param path gpio路径
     */
    public GpioControl( String path) throws IOException {
        File gpioFile = new File(path);
        mControlFile = new BufferedWriter(new FileWriter(gpioFile, false));
    }

    /**
     * 主机gpio上电.
     *
     * @param gpio gpio
     * @return 结果
     */
    public boolean powerOnDevice(int... gpio) {
        for (int aGpio : gpio) {
            try {
                powerOnDevice(aGpio);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 主机gpio下电.
     *
     * @param gpio gpio
     * @return 结果
     */
    public boolean powerOffDevice(int... gpio) {
        for (int aGpio : gpio) {
            try {
                powerOffDevice(aGpio);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 外部扩展gpio上电.
     *
     * @param gpio gpio
     * @return 结果
     */
    public boolean powerOnDeviceOut(int... gpio) {
        for (int aGpio : gpio) {
            try {
                powerOnDeviceOut(aGpio);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 外部扩展gpio下电.
     *
     * @param gpio gpio
     * @return 结果
     */
    public boolean powerOffDeviceOut(int... gpio) {
        for (int aGpio : gpio) {
            try {
                powerOffDeviceOut(aGpio);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 主机gpio上电.
     *
     * @param gpio gpio
     */
    private void powerOnDevice(int gpio) throws IOException {
        mControlFile.write("-wmode" + gpio + " 0");   //将GPIO99设置为GPIO模式
        mControlFile.flush();
        mControlFile.write("-wdir" + gpio + " 1");        //将GPIO99设置为输出模式
        mControlFile.flush();
        mControlFile.write("-wdout" + gpio + " 1");   //上电IO口调整
        mControlFile.flush();
    }

    /**
     * 主机gpio下电.
     *
     * @param gpio gpio
     */
    private void powerOffDevice(int gpio) throws IOException {
        mControlFile.write("-wmode" + gpio + " 0");   //将GPIO99设置为GPIO模式
        mControlFile.flush();
        mControlFile.write("-wdir" + gpio + " 1");        //将GPIO99设置为输出模式
        mControlFile.flush();
        mControlFile.write("-wdout" + gpio + " 0");   //下电IO口调整
        mControlFile.flush();
    }

    /**
     * 外部扩展gpio上电.
     *
     * @param gpio gpio
     */
    private void powerOnDeviceOut(int gpio) throws IOException {
        mControlFile.write(gpio + "on");   //上电IO口调整
        mControlFile.flush();
    }

    /**
     * 外部扩展gpio下电.
     *
     * @param gpio gpio
     */
    private void powerOffDeviceOut(int gpio) throws IOException {
        mControlFile.write(gpio + "off");   //下电IO口调整
        mControlFile.flush();
    }
}
