package com.example;

import java.util.ArrayList;
import java.util.List;

import static com.example.Constants.*;

public class Family {

    private Member familyHead;

    public void addHead(String name, String gender) {
        if (Gender.isValid(gender)) {
            this.familyHead = new Member(name, Gender.valueOf(gender));
        }
    }

    public void addSpouse(String memberName, String spouseName, String gender) {
        Member member = findMemberByName(familyHead, memberName);
        if (member != null && Gender.isValid(gender) && spouseName != null) {
            Member spouse = new Member(spouseName, Gender.valueOf(gender));
            spouse.addSpouse(member);
            member.addSpouse(spouse);
        }
    }

    public String addChild(String motherName, String childName, String gender) {
        Member member = findMemberByName(familyHead, motherName);
        if (member == null) {
            return PERSON_NOT_FOUND;
        } else if (!Gender.Female.equals(member.getGender()) || childName == null || !(Gender.isValid(gender))) {
            return CHILD_ADDITION_FAILED;
        } else {
            Member child = new Member(childName, Gender.valueOf(gender), member, member.getSpouse());
            member.addChild(child);
            return CHILD_ADDITION_SUCCEEDED;
        }
    }

    public Member findMemberByName(Member headMember, String name) {
        if (name == null || headMember == null) {
            return null;
        }
        // search head
        if (name.equals(headMember.getName())) {
            return headMember;
        } else if (headMember.getSpouse() != null && name.equals(headMember.getSpouse().getName())) {
            return headMember.getSpouse();
        }
        // assuming only female will have children
        Member femaleMember = headMember.getGender() == Gender.Female ? headMember : headMember.getSpouse();
        Member memberToFind = null;
        if (femaleMember != null) {
            List<Member> children = femaleMember.getChildren();
            for (Member child : children) {
                memberToFind = findMemberByName(child, name);
                if (memberToFind != null) {
                    break;
                }
            }
        }
        return memberToFind;
    }

    public String getRelationship(String name, String relationship) {
        String relations;
        Member member = findMemberByName(familyHead, name);
        if (member == null) {
            relations = PERSON_NOT_FOUND;
        } else if (relationship == null) {
            relations = PROVIDE_VALID_RELATION;
        } else {
            relations = getRelationship(member, relationship);
        }
        return relations;
    }

    private String getRelationship(Member member, String relationship) {
        List<Member> relations = new ArrayList<>();
        switch (relationship) {
            case DAUGHTER:
                relations = member.searchChild(Gender.Female);
                break;
            case SON:
                relations = member.searchChild(Gender.Male);
                break;
            case SIBLINGS:
                relations = member.searchSiblings();
                break;
            case SISTER_IN_LAW:
                if (member.getSpouse() != null) {
                    relations = member.getSpouse().searchSiblings(Gender.Female);
                }
                break;
            case BROTHER_IN_LAW:
                if (member.getSpouse() != null) {
                    relations = member.getSpouse().searchSiblings(Gender.Male);
                }
                break;
            case MATERNAL_AUNT:
                if (member.getMother() != null)
                    relations = member.searchAuntsOrUncles(member.getMother(), Gender.Female);
                break;
            case PATERNAL_AUNT:
                if (member.getFather() != null)
                    relations = member.searchAuntsOrUncles(member.getFather(), Gender.Female);
                break;
            case MATERNAL_UNCLE:
                if (member.getMother() != null)
                    relations = member.searchAuntsOrUncles(member.getMother(), Gender.Male);
                break;
            case PATERNAL_UNCLE:
                if (member.getFather() != null)
                    relations = member.searchAuntsOrUncles(member.getFather(), Gender.Male);
                break;
            default:
                relations = new ArrayList<>();
                break;
        }
        return (relations.size() == 0) ? NONE : getStringForm(relations);
    }

    private String getStringForm(List<Member> relations) {
        StringBuilder sb = new StringBuilder("");
        for (Member relationMember : relations) {
            sb.append(relationMember.getName()).append(" ");
        }
        return sb.toString().trim();
    }

}
