package com.softcon.meetinguniv;

import android.os.Parcel;
import android.os.Parcelable;

public class MTeamMemberRecyclerItem implements Parcelable {

    public int memProfile;

   public MTeamMemberRecyclerItem(int memProfile){
       this.memProfile = memProfile;
   }

    protected MTeamMemberRecyclerItem(Parcel in) {
        memProfile = in.readInt();
    }

    public static final Creator<MTeamMemberRecyclerItem> CREATOR = new Creator<MTeamMemberRecyclerItem>() {
        @Override
        public MTeamMemberRecyclerItem createFromParcel(Parcel in) {
            return new MTeamMemberRecyclerItem(in);
        }

        @Override
        public MTeamMemberRecyclerItem[] newArray(int size) {
            return new MTeamMemberRecyclerItem[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(memProfile);
    }
}
