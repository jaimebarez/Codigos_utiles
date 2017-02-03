/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.utilities.javafx;
//dd/MM/YYYY
//15/09/2014

import java.io.File;
import javafx.stage.FileChooser;

public class UserHomeInitFileChooser {

    private static final String USERHOME = System.getProperty("user.home");

    public static FileChooser create() {
        FileChooser fileChooser = new FileChooser();

        File initialFile = getUserHomeFileIfExists();
        if (initialFile != null) {
            fileChooser.setInitialDirectory(initialFile);
        }
        return fileChooser;
    }

    private static File getUserHomeFileIfExists() {

        if (USERHOME != null) {
            File file = new File(USERHOME);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }
}
