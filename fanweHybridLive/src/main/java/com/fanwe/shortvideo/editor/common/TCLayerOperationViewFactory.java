package com.fanwe.shortvideo.editor.common;

import android.content.Context;
import android.view.View;

import com.fanwe.live.R;
import com.fanwe.shortvideo.editor.common.widget.layer.TCLayerOperationView;


/**
 * Created by hanszhli on 2017/6/21.
 * <p>
 * 创建 OperationView的工厂
 */

public class TCLayerOperationViewFactory {

    public static TCLayerOperationView newOperationView(Context context) {
        return (TCLayerOperationView) View.inflate(context, R.layout.layout_layer_operation_view, null);
    }
}
