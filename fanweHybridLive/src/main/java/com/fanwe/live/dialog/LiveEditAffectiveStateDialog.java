package com.fanwe.live.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.GridView;
import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserEditActivity;
import com.fanwe.live.adapter.SelectLabelAdapter;
import com.fanwe.live.model.SelectLabelModel;
import java.util.List;

/**
 * Created by shibx on 2016/7/15.
 */
public class LiveEditAffectiveStateDialog extends SDDialogBase {

    private SelectLabelAdapter mAdapter;
    private GridView mGridView;
    private List<SelectLabelModel> mList;

    private ChooseAffectiveStateListener mListener;

    public LiveEditAffectiveStateDialog(List<SelectLabelModel> list, Activity activity) {
        super(activity);
        this.mList = list;
        setContentView(R.layout.dialog_edit_affective_state);
        setCanceledOnTouchOutside(true);
        padding(0,0,0,0);
        initView();
    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.gv_edit_affective_state);
        mAdapter = new SelectLabelAdapter(mList,getOwnerActivity());
        mGridView.setAdapter(mAdapter);
        mAdapter.getSelectManager().setMode(SDSelectManager.Mode.SINGLE_MUST_ONE_SELECTED);
        mAdapter.setItemClickCallback(new SDItemClickCallback<SelectLabelModel>()
        {
            @Override
            public void onItemClick(int position, SelectLabelModel item, View view)
            {
                if(mListener != null) {
                    if(mAdapter.getSelectManager().getSelectedItem() ==item) {
                        mListener.onChooseState(LiveUserEditActivity.FLAG_AFFECTIVE_STATE,item,false);
                    } else {
                        mListener.onChooseState(LiveUserEditActivity.FLAG_AFFECTIVE_STATE,item,true);
                    }
                    mAdapter.getSelectManager().performClick(item);
                    dismiss();
                }
            }
        });
    }

    public void showBottom(int index) {
        showBottom();
        mAdapter.getSelectManager().setSelected(index, true);
    }

    public void setChooseAffectiveStateListener(ChooseAffectiveStateListener listener) {
        this.mListener = listener;
    }

    public interface ChooseAffectiveStateListener {
        void onChooseState(int flag, SelectLabelModel model, boolean isChanged);
    }
}
