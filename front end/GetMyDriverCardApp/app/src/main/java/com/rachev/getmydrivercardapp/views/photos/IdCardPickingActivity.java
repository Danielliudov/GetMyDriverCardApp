package com.rachev.getmydrivercardapp.views.photos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.models.BaseRequest;
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.utils.Methods;
import com.rachev.getmydrivercardapp.views.signature.SignatureActivity;
import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.File;
import java.util.List;

public class IdCardPickingActivity extends BaseImagePickingActivity implements View.OnClickListener
{
    private BaseRequest mBaseRequest;
    private File mIdPicFile;
    
    @BindView(R.id.pick_id_bitmap)
    Button mIdImagePickButton;
    
    @BindView(R.id.choosen_pic)
    ImageView mIdBitmapView;
    
    @BindView(R.id.btn_next)
    Button mNextButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card_picking);
        setTitle("Step 3");
        
        ButterKnife.bind(this);
        
        mBaseRequest = (BaseRequest) getIntent().getSerializableExtra("request");
        
        mIdImagePickButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        
        getStoragePermission();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case ImagePicker.IMAGE_PICKER_REQUEST_CODE:
                mIdBitmapView.setVisibility(View.VISIBLE);
                
                if (data == null)
                    return;
                
                List<String> paths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
                Bitmap bitmap = Methods.convertFileToBitmap(new File(paths.get(0)));
                mIdBitmapView.setImageBitmap(bitmap);
                mIdPicFile = Methods.convertBitmapToFile(bitmap, this,
                        System.currentTimeMillis() + Constants.Strings.PNG_SUFFIX);
                mBaseRequest.getImageAttachment().setIdCardImage(
                        Methods.encodeBitmapToBase64String(mIdPicFile
                                .getPath()));
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.pick_id_bitmap:
                new ImagePicker.Builder(this)
                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .extension(ImagePicker.Extension.PNG)
                        .scale(600, 600)
                        .allowMultipleImages(false)
                        .enableDebuggingMode(true)
                        .build();
                break;
            case R.id.btn_next:
                Intent intent = new Intent(this, SignatureActivity.class);
                intent.putExtra("request", mBaseRequest);
                intent.putExtra("path1", mIdPicFile.getPath());
                intent.putExtra("path2", "");
                intent.putExtra("path3", "");
                startActivity(intent);
                break;
        }
    }
}
