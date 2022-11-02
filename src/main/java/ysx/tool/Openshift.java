package ysx.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Openshift {
    static String LoginCommend = "oc login -u apikey -p NUXZ3x75wgnuoWTPj-7xCxnJirRl5jzUQaKaGyDZebQq https://c100-e.jp-tok.containers.cloud.ibm.com:30114";
    static String PropertSelect = "oc project test03";
    static String getPods = "oc get pods";
    static String ReStart = "oc rollout ";
    static List<String> pods = Arrays.asList("commonutils-ymfg-digital2", "");

    public static void copyLog() {

    }

    public static List<String> gtepodsList() {
        List<String> podsList = new ArrayList<>();
        List<String> podsLineList = new ArrayList<>();
        podsLineList = excuteCMD(getPods);
        Pattern pattern = Pattern.compile("[(.+)(\\s+)(1/1)(\\s+)(Running)]");

        for (String str : podsLineList) {
            Matcher matcher = pattern.matcher(str);
            String string = matcher.group(1);
            System.out.println(string);
        }

        return podsList;
    }

    public static List<String> excuteCMD(String cmd) {
        Process process = null;
        List<String> result = new ArrayList<String>();
        try {
            Runtime runtime = Runtime.getRuntime();
            System.out.println();
            System.out.println("*********************");
            System.out.println(cmd);
            // System.out.println("Command execution start ");
            process = runtime.exec(cmd);
            // System.out.println("Command execution finish ");
            result = new InStreamReader().reader(process.getErrorStream());
            if (!result.isEmpty()) {
                System.out.println("ErrorStream :Command execution failed ");
                return result;
            } else {
                result = new InStreamReader().reader(process.getInputStream());
                if (!result.isEmpty()) {
                    return result;
                } else {
                    System.out.println("InputStream:Command execution failed");
                    System.exit(1);
                }
            }

        } catch (Exception e) {
            System.out.println("Command execution failed");
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }

        return result;
    }

    static class InStreamReader implements Runnable {
        InputStream inputStream;
        List<String> list = new ArrayList<String>();

        public List<String> reader(InputStream inputStream) {
            this.inputStream = inputStream;
            list = new ArrayList<String>();
            run();
            return list;
        }

        @Override
        public void run() {
            try {
                String line = null;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                    list.add(line);
                }
            } catch (Exception e) {
                System.out.println("Command return read failed");
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                    // System.out.println("inputStream close success");
                } catch (Exception e2) {
                    System.out.println("inputStream close failed");
                }
            }
        }

    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String openshiftLoginCommand = "oc login -u apikey -p NUXZ3x75wgnuoWTPj-7xCxnJirRl5jzUQaKaGyDZebQq https://c100-e.jp-tok.containers.cloud.ibm.com:30114";
        // excuteCMD(openshiftLoginCommand);
        // excuteCMD("oc project test03");
        excuteCMD("oc get pods ");
        // gtepodsList();
    }

}
