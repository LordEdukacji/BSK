package pl.edu.pg.student.s193483;

import java.io.File;

/**
 * @class UsbDetector
 * @brief Background USB detector
 * @details To be run in the background, provides an updated list of drives
 */
public class UsbDetector implements Runnable {
    UsbHolder usbHolder; //!< Class which will handle the USB drive

    public UsbDetector(UsbHolder usbHolder) {
        this.usbHolder = usbHolder;
    }

    /**
     * @brief Obtain list of roots in the background
     */
    @Override
    public void run() {
        while (true) {
            try {
                // check 4 times a second
                Thread.sleep(250);

                // send updated drive list to holder
                usbHolder.updateRoots(File.listRoots());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
