package net.accko.whatsnewparsing;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.ExecutionException;

public class WhatsNewParsing {

    private static String packageName;

    public static String getPackageName(String getPackageName) {
        packageName = getPackageName;
        return packageName;
    }

    public static class getWhatsNewTask extends AsyncTask<String, String, String> {
        String original, replaced;
        String PlayStoreLink = "https://play.google.com/store/apps/details?id=" + packageName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect(PlayStoreLink).get();
                original = doc.getElementsByAttributeValue("class", "recent-change").text();
                replaced = original.replace("-", "\n- ").replaceFirst("\n", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return replaced;
        }
    }

    public static String getWhatsNew() {
        String SavedWhatsNewStr = null;
        try {
            SavedWhatsNewStr = new getWhatsNewTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return SavedWhatsNewStr;
    }
}