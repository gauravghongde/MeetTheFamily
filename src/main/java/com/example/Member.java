package com.example;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

enum Gender {
    Male, Female;

    public static boolean isValid(String gender) {
        if (gender == null) {
            return false;
        }
        final EnumSet<Gender> validGenders = EnumSet.of(Gender.Male, Gender.Female);
        boolean valid;
        try {
            valid = validGenders.contains(Gender.valueOf(gender));
        } catch (IllegalArgumentException e) {
            valid = false;
        }
        return valid;
    }
}

public class Member {
    private String name;
    private Gender gender;
    private Member mother;
    private Member father;
    private Member spouse;
    private final List<Member> children = new ArrayList<>();

    public Member(String name, Gender gender, Member mother, Member father) {
        this.name = name;
        this.gender = gender;
        this.mother = mother;
        this.father = father;
        this.spouse = null;
    }

    public Member(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
        this.mother = null;
        this.father = null;
        this.spouse = null;
    }

    public void addChild(Member child) {
        this.children.add(child);
    }

    public void addSpouse(Member spouse) {
        this.spouse = spouse;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Member getMother() {
        return mother;
    }

    public Member getFather() {
        return father;
    }

    public Member getSpouse() {
        return spouse;
    }

    public List<Member> getChildren() {
        return children;
    }

    public List<Member> searchChild(Gender gender) {
        List<Member> members = new ArrayList<>();
        for (Member child : this.children) {
            if (child.getGender().equals(gender)) {
                members.add(child);
            }
        }
        return members;
    }

    public List<Member> searchSiblings(Gender gender) {
        List<Member> members = new ArrayList<>();
        if (this.mother != null) {
            for (Member sibling : this.mother.children) {
                if (!this.name.equals(sibling.name) && gender.equals(sibling.gender)) {
                    members.add(sibling);
                }
            }
        }
        return members;
    }

    public List<Member> searchSiblings() {
        List<Member> members = new ArrayList<>();
        if (this.mother != null) {
            for (Member sibling : this.mother.children) {
                if (!this.name.equals(sibling.name)) {
                    members.add(sibling);
                }
            }
        }
        return members;
    }

//    public List<Member> searchInLaws(Gender gender) {
//        List<Member> members = new ArrayList<>();
//        if (this.spouse != null && this.spouse.mother != null) {
//            members.addAll(this.spouse.mother.searchChild(gender));
//        }
//
//        if (this.mother != null) {
//            members.addAll(this.mother.searchChild(gender));
//        }
//        return members;
//    }

    public List<Member> searchAuntsOrUncles(Member parent, Gender gender) {
        List<Member> members = new ArrayList<>();
        for (Member uncleOrAunt : parent.searchSiblings()) {
            if (uncleOrAunt.getGender().equals(gender)) {
                members.add(uncleOrAunt);
            }
        }
        return members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(name, member.name) &&
                gender == member.gender &&
                Objects.equals(mother, member.mother) &&
                Objects.equals(father, member.father) &&
                Objects.equals(spouse, member.spouse) &&
                Objects.equals(children, member.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, mother, father, spouse, children);
    }
}
