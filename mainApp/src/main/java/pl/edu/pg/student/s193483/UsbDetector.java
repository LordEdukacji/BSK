package pl.edu.pg.student.s193483;

import java.io.File;

public class UsbDetector implements Runnable {
    UsbHolder usbHolder;

    public UsbDetector(UsbHolder usbHolder) {
        this.usbHolder = usbHolder;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(250);

                usbHolder.updateRoots(File.listRoots());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
