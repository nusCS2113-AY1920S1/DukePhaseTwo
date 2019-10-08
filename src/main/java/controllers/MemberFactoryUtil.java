package controllers;

import models.member.Member;

public class MemberFactoryUtil {
    private Member newMember;

    /**
     * Method to create a new member.
     * @param input Input containing details of member to be added (name, phone, email).
     * @return Member with the relevant details. Index number is set later when adding to list.
     */
    public boolean memberIsCreated(String input) {
        String name = "No name";
        String phone = "No phone number";
        String email = "No email address";

        boolean nameCreatedFlag = false;
        String [] memberDetails = input.split(" ");
        for (int i = 0; i < memberDetails.length; i++) {
            String s = memberDetails[i];
            switch (s.substring(0,2)) {
            case "n/":
                name = s.substring(2);
                nameCreatedFlag = true;
                break;
            case "p/":
                phone = s.substring(2);
                break;
            case "e/":
                email = s.substring(2);
                break;
            default:
                break;
            }
        }
        if (nameCreatedFlag) {
            this.newMember = new Member(name, phone, email, 0);
            return true;
        } else {
            return false;
        }
    }

    public Member getNewMember() {
        return this.newMember;
    }
}
