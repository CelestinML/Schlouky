package com.example.schlouky;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.Comparator;

public class Player implements Parcelable
{
    public String name;
    public Boolean buveur;
    public Uri photoUri;
    public String photoPath;
    public int chance; //chance d'apparaitre à la prochaine question.

    Player(String name, Boolean buveur, Uri photoUri, String photoPath)
    {
        this.name = name;
        this.buveur = buveur;
        this.photoUri = photoUri;
        this.photoPath = photoPath;
    }

    protected Player(Parcel in) {
        name = in.readString();
        byte tmpBuveur = in.readByte();
        buveur = tmpBuveur == 0 ? null : tmpBuveur == 1;
        photoUri = in.readParcelable(Uri.class.getClassLoader());
        photoPath = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeByte((byte) (buveur == null ? 0 : buveur ? 1 : 2));
        parcel.writeParcelable(photoUri, i);
        parcel.writeString(photoPath);
    }
}

/**
 * Permet de trier une liste de joueurs pour avoir les buveurs en premier
 */
class PlayerDrinkingCompare implements Comparator<Player>
{
    public int compare(Player p1, Player p2)
    {
        if(p1.buveur && !p2.buveur) return -1;
        else if(!p1.buveur && p2.buveur) return 1;
        return 0;
    }
}

/**
 * Permet de trier une liste de joueurs en prenannt en compte leur chance d'apparition.
 * Plus un joueur est chanceux, plus il doit être en début de liste.
 */
class PlayerChanceCompare implements Comparator<Player>
{
    public int compare(Player p1, Player p2)
    {
        if(p1.chance > p2.chance) return -1;
        else if(p1.chance < p2.chance) return 1;
        return 0;
    }
}