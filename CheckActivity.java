package com.manoj.workmanager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import java.util.concurrent.TimeUnit;

/**
 * @Author Manoj Suthar
 * WorkManager created on 15/10/2019
 * {@link MyWorker}
 */
public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compareDateWithServer();
    }

    private void compareDateWithServer() {
        /*check if(date is matched)
         * {
         *     setBackgroundTask();
         * }
         * */
    }

    private void setBackgroundTask() {
        /**
         * after 48 hours notification will be display.
         * */
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setInitialDelay(48, TimeUnit.HOURS)
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueue(request);

        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(request.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                /*After you enqueue your work, WorkManager allows you to check on
                its status. This information is available in a WorkInfo object,
                which includes the id of the work, its tags, its current State,
                and any output data.*/

            }
        });
    }
}
