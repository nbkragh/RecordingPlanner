package com.opgaver.recordingplanner.persistence;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static com.j256.ormlite.android.apptools.OrmLiteConfigUtil.writeConfigFile;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    public static void main(String[] args) throws SQLException, IOException {
        System.out.println("new File(\".\").getAbsolutePath() = " + new File(".").getAbsolutePath());
        writeConfigFile("ormlite_config.txt");

    }
}
