package com.fanwe.live.utils;

import android.app.Activity;
import android.view.View;

import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.handler.PhotoHandler;

/**
 * Created by Administrator on 2016/8/8.
 */
public class PhotoBotShowUtils
{
    public final static int DIALOG_CAMERA = 0;
    public final static int DIALOG_ALBUM = 1;
    public final static int DIALOG_BOTH = 2;

    public static void openBotPhotoView(Activity activity, final PhotoHandler photoHandler, final int type)
    {
        if (photoHandler == null)
        {
            return;
        }
        String[] arrOption = null;
        if (type == DIALOG_CAMERA)
        {
            arrOption = new String[]{"拍照"};
        } else if (type == DIALOG_ALBUM)
        {
            arrOption = new String[]{"相册"};
        } else
        {
            arrOption = new String[]{"拍照", "相册"};
        }

        SDDialogMenu dialog = new SDDialogMenu(activity);

        dialog.setItems(arrOption);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setmListener(new SDDialogMenu.SDDialogMenuListener()
        {

            @Override
            public void onItemClick(View v, int index, SDDialogMenu dialog)
            {
                if (type == DIALOG_CAMERA)
                {
                    photoHandler.getPhotoFromCamera();
                } else if (type == DIALOG_ALBUM)
                {
                    photoHandler.getPhotoFromAlbum();
                } else
                {
                    switch (index)
                    {
                        case 0:
                            photoHandler.getPhotoFromCamera();
                            break;
                        case 1:
                            photoHandler.getPhotoFromAlbum();
                            break;
                        default:
                            break;
                    }
                }
                dialog.dismiss();
            }

            @Override
            public void onDismiss(SDDialogMenu dialog)
            {
            }

            @Override
            public void onCancelClick(View v, SDDialogMenu dialog)
            {
            }
        });
        dialog.showBottom();
    }
}
