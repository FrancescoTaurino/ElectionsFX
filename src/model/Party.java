package model;

public class Party {
    private String name;
    private int votes;

    public Party(String name, int votes) {
        this.name = name;
        this.votes = votes;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof Party)) return false;

        Party party = (Party) object;

        return this.name.equals(party.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

}
