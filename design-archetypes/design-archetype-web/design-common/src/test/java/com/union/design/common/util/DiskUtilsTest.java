package com.union.design.common.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class DiskUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void compress() throws IOException {
        final String rootDir = "D:\\api";
        final String sourceDir = "apache-ant-1.10.7";
        final String snapshotArchive = sourceDir+".zip";
        String outputFile = Paths.get(rootDir, snapshotArchive).toString();
        final Checksum checksum = new CRC32();
        DiskUtils.compress(rootDir,sourceDir,outputFile,checksum);
    }

    @Test
    public void decompress() throws IOException {
        final String readerPath = "D:\\api\\apache-ant-1.10.7.zip";
        final String snapshotArchive = "apache-ant-1.10.7";
        final String sourceFile = Paths.get("D:\\", snapshotArchive).toString();
        final Checksum checksum = new CRC32();
        DiskUtils.decompress(readerPath,sourceFile,checksum);
    }

    @Test
    public void test(){
        String permissionResource="private:.*:.*";
        String Resource="private:*:config/1";
        System.out.println(Pattern.matches(permissionResource, Resource));
    }

}