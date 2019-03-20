package com.fanwe.live.model.custommsg;

import com.fanwe.library.utils.SDToast;
import com.fanwe.live.LiveConstant.CustomMsgType;
import com.fanwe.live.model.UserJoinGuard;
import com.fanwe.live.model.UserJoinMount;
import com.fanwe.live.model.UserModel;

public class CustomMsgViewerJoin extends CustomMsg
{

    public CustomMsgViewerJoin()
    {
        super();
        setType(CustomMsgType.MSG_VIEWER_JOIN);
    }

    public void setSortNumber(int sortNumber)
    {
        UserModel userModel = getSender();
        if (userModel == null)
        {
            SDToast.showToast("setSort_num fail usermodel is null");
            return;
        }
        if (sortNumber <= 0)
        {
            sortNumber = userModel.getUser_level();
        }
        userModel.setSort_num(sortNumber);
    }

    private UserJoinGuard guard;
    private UserJoinMount mount;

    public UserJoinMount getMount() {
        return mount;
    }

    public UserJoinGuard getGuard() {
        return guard;
    }

    public void setGuard(UserJoinGuard guard) {
        this.guard = guard;
    }

    public void setMount(UserJoinMount mount) {
        this.mount = mount;
    }


    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }

        if (!(o instanceof CustomMsgViewerJoin))
        {
            return false;
        }

        CustomMsgViewerJoin model = (CustomMsgViewerJoin) o;
        UserModel user = model.getSender();
        if (user == null)
        {
            return false;
        }
        if (!user.equals(getSender()))
        {
            return false;
        }

        return true;
    }

}
