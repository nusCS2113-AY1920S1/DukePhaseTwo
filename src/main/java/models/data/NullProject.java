package models.data;

import models.member.Member;
import models.member.MemberList;

public class NullProject implements IProject {
    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public MemberList getMembers() {
        return null;
    }

    @Override
    public int getNumOfMembers() {
        return 0;
    }

    @Override
    public void addMember(Member newMember) {
        /*
        Empty method
         */
    }
}
