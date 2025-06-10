package pl.edu.pg.student.s193483;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @class UsbHolder Handles selecting the USB drive and finding the key file
 */
public class UsbHolder {
    private final JLabel usbStatus;     //!< Reference to the USB status bar to provide USb information
    private final JLabel status;        //!< Reference to the status bar to provide general information
    File[] roots = new File[0];         //!< All found roots
    public File usb = null;             //!< The root determined to be the USB drive
    public byte[] privateKeyBytes;      //!< Bytes of the private key

    /**
     * @brief Default constructor
     * @param usbStatus USB status bar
     * @param status General status bar
     */
    public UsbHolder(JLabel usbStatus, JLabel status) {
        this.usbStatus = usbStatus;
        this.status = status;
    }

    /**
     * @brief Update the list of roots and determine the USB drive if the list changed
     * @param newRoots List of all roots provided by the detector
     */
    public void updateRoots(File[] newRoots) {
        List<File> detected = new ArrayList<File>();

        // more roots
        if (newRoots.length > roots.length && roots.length != 0) {
            new_root_loop:
            for (File newRoot : newRoots) {
                for (File oldRoot : roots) {
                    if (newRoot.equals(oldRoot)) continue new_root_loop;
                }
                detected.add(newRoot);
            }

            // multiple drives at the same time
            // insert one at a time to remove ambiguity
            if (detected.size() > 1) {
                usbStatus.setText("Multiple new drives detected, insert one at a time");
            }
            // one new root
            else if (detected.size() == 1) {
                usb = detected.getFirst();
                usbStatus.setText("Detected USB drive " + usb.toString());

                loadPrivateKeyBytes();
            }
        }

        // removed drive
        usb_removed_check:
        if (newRoots.length < roots.length) {
            for (File newRoot : newRoots) {
                if (newRoot.equals(usb)) break usb_removed_check;
            }

            usb = null;
            usbStatus.setText("USB drive removed");
        }

        // update roots list
        roots = newRoots;
    }

    /**
     * @brief Find the file with the key
     * @details Only searches one level deep, can't look into folders. There should only be one .key file on the drive for this to work.
     */
    public void loadPrivateKeyBytes() {
        File[] keyFiles = usb.listFiles((_, name) -> name.endsWith(".key"));

        if (keyFiles != null) {
            // no keys
            if (keyFiles.length == 0) {
                usbStatus.setText("Detected USB drive " + usb.toString() + " - no keys detected");
            }
            // too many keys
            else if (keyFiles.length > 1) {
                usbStatus.setText("Detected USB drive " + usb.toString() + " - multiple keys detected");
            }
            // one key
            else {
                usbStatus.setText("Detected USB drive " + usb.toString() + " - detected key file " + keyFiles[0].getName());
                try (FileInputStream fileInputStream = new FileInputStream(keyFiles[0].getAbsolutePath())) {
                    privateKeyBytes = fileInputStream.readAllBytes();
                } catch (IOException e) {
                    status.setText("Could not read private key file");
                }
            }
        }
    }
}
