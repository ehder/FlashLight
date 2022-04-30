package com.der.flashlight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {

    ImageButton img_on, img_off;
    Button on_off_btn;
    boolean on_off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img_on = findViewById(R.id.img_on);
        img_off = findViewById(R.id.img_off);
        on_off_btn = findViewById(R.id.on_off_btn);


        Dexter.withContext(this).withPermissions(Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                runFlishLight();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

            }
        }).check();

        on_off_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (on_off == true){

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                        try {
                            String cameraId = cameraManager.getCameraIdList()[0];
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                cameraManager.setTorchMode(cameraId, false);
                            }
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }


                    }
                    on_off_btn.setText("Light OFF");
                    img_off.setImageResource(R.drawable.lightofft);
                    img_off.setVisibility(View.VISIBLE);
                    img_on.setVisibility(View.GONE);
                    on_off = false;
                    return;
                }else if (on_off == false){

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                        try {
                            String cameraId = cameraManager.getCameraIdList()[0];
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                cameraManager.setTorchMode(cameraId, true);
                            }
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }


                    }

                    on_off_btn.setText("Light ON");
                    img_on.setImageResource(R.drawable.lighton);
                    img_on.setVisibility(View.VISIBLE);
                    img_off.setVisibility(View.GONE);
                    on_off = true;
                    return;
                }
            }
        });



    }

    private void runFlishLight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try {
                String cameraId = cameraManager.getCameraIdList()[0];
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode(cameraId, true);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        img_on.setImageResource(R.drawable.lighton);
        img_off.setVisibility(View.GONE);
        on_off_btn.setText("Light ON");
        on_off = true;
    }
}