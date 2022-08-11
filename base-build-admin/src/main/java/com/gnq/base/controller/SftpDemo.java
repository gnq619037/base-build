package com.gnq.base.controller;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

/**
 * @Desc:
 * @Author: guonanqing
 * @Date: 2022/8/11 11:20
 * @Version: 1.0
 */
public class SftpDemo {
    static Session sshSession = null;

    /**
     * 获取ChannelSftp
     */
    public static ChannelSftp getConnectIP(String host, String sOnlineSftpPort, String username, String password) {
        int port = 0;
        if (!("".equals(sOnlineSftpPort)) && null != sOnlineSftpPort) {
            port = Integer.parseInt(sOnlineSftpPort);
        }
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sftp;
    }

    /**
     * 上传
     */
    public static void upload(String directory, String uploadFile, ChannelSftp sftp) {
        FileInputStream io = null;
        try {
            sftp.cd(directory);

            File file = new File(uploadFile);
            io = new FileInputStream(file);
            sftp.put(io,  file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if(null!=io) {
                try {
                    io.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sftp.isConnected()) {
                sshSession.disconnect();
                sftp.disconnect();
            }

        }
    }

    static boolean deleteDirFiles(String newsFile, ChannelSftp sftp) {
        try {
            sftp.cd(newsFile);
            ListIterator a = sftp.ls(newsFile).listIterator();
            while (a.hasNext()) {
                ChannelSftp.LsEntry oj = (ChannelSftp.LsEntry) a.next();
                System.out.println(oj.getFilename());
                SftpDemo.delete(newsFile, oj.getFilename(), sftp);
                //fileList.add((String) a.next());
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    /**
     * 上传本地文件到sftp指定的服务器，
     * @param directory  目标文件夹
     * @param uploadFile   本地文件夹
     * @param sftp   sftp地址
     * @param remoteFileName 重命名的文件名字
     * @param isRemote  是否需要重命名  是true 就引用remoteFileName 是false就用默认的文件名字
     */
    public static void upload(String directory, String uploadFile, ChannelSftp sftp,String remoteFileName,boolean isRemote) {
        FileInputStream io = null;
        try {
            boolean isExist=false;
            try {
                SftpATTRS sftpATTRS = sftp.lstat(directory);
                isExist = true;
                isExist=sftpATTRS.isDir();
            } catch (Exception e) {
                if (e.getMessage().toLowerCase().equals("no such file")) {
                    isExist = false;
                }
            }
            if(!isExist){
                boolean existDir = SftpDemo.isExistDir(directory, sftp);
                if (!existDir) {
                    String pathArry[] = directory.split("/");
                    StringBuffer Path = new StringBuffer("/");
                    for (String path : pathArry) {
                        if (path.equals("")) {
                            continue;
                        }
                        Path.append(path + "/");
                        if (!SftpDemo.isExistDir(Path+"", sftp)) {
                            // 建立目录
                            sftp.mkdir(Path.toString());
                            // 进入并设置为当前目录
                        }
                        sftp.cd(Path.toString());
                    }
                }
            }
            sftp.cd(directory);
            File file = new File(uploadFile);
            io = new FileInputStream(file);
            if(isRemote){
                sftp.put(io, remoteFileName);
            }else{
                sftp.put(io, file.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null!=io) {
                try {
                    io.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sftp.isConnected()) {
                sshSession.disconnect();
                sftp.disconnect();
            }

        }
    }


    public static boolean isExistDir(String path, ChannelSftp sftp) {
        boolean isExist = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(path);
            isExist = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isExist = false;
            }
        }
        return isExist;

    }


    /**
     * 上传
     */
    public static List<String> uploadZip(String directory, String uploadFile, ChannelSftp sftp, List<String> filePath, String remoteName) {
        try {
            List<String> list =new ArrayList<>();
            boolean existDir = SftpDemo.isExistDir(directory, sftp);
            if (!existDir) {
                sftp.mkdir(directory);
            }
            sftp.cd(directory);
            int i = 1;
            for (String newPath : filePath) {
                FileInputStream io = null;
                try {
                    File file = new File(uploadFile + newPath);
                    io = new FileInputStream(file);
                    sftp.put(io, remoteName + "-" + i + ".jpg");
                    io.close();
                    list.add(remoteName + "-" + i + ".jpg");
                    i++;
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    if (null != io) {
                        try {
                            io.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return list;
        } catch (SftpException e) {
            e.getMessage();
            return null;
        } finally {
            if (sftp.isConnected()) {
                sshSession.disconnect();
                sftp.disconnect();
            }
        }
    }

    /**
     * 下载
     */
    public static void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sftp.isConnected()) {
                sshSession.disconnect();
                sftp.disconnect();
            }

        }
    }

    /**
     * 查看
     */
    public static List<String> check(String directory, ChannelSftp sftp) {
        List<String> fileList = new ArrayList<>();
        try {
            sftp.cd(directory);
            ListIterator a = sftp.ls(directory).listIterator();
            while (a.hasNext()) {
                ChannelSftp.LsEntry oj = (ChannelSftp.LsEntry) a.next();
                System.out.println(oj.getFilename());
                //fileList.add((String) a.next());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sftp.isConnected()) {
                sshSession.disconnect();
                sftp.disconnect();
            }

        }
        return fileList;
    }

    /**
     * 删除
     */
    public static void delete(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createDir(String dir, String permissions, ChannelSftp sftp) throws Exception {
        sftp.mkdir(dir);
        sftp.chmod(Integer.parseInt(permissions, 8), dir);
    }


    public static void main(String[] args) throws Exception {

        //服务器地址,端口，用户名，密码
        ChannelSftp ftp = getConnectIP("120.46.129.60", "22", "root", "Qing@619037");
        createDir("/opt/20220603", "775", ftp);
//        ftp.chmod(775, "/opt/20220601");
        upload("/opt/20220603", "D:\\file\\channel-write.txt", ftp);
    }
}
