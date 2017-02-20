package com.example.nicta.gdb;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nicta on 2017-02-19.
 */

public class Enums {

    public enum Type
    {
        Bronze(0),
        Silver(1),
        Gold(2),
        Leader(3);

        private int value;
        private Type(int value){
            this.value=value;
        }

        private static Map map = new HashMap<>();

        static {
            for (Type type : Type.values()) {
                map.put(type.value, type);
            }
        }

        public static Type valueOf(int type) {
            return (Type)map.get(type);
        }

        public int getValue() {
            return value;
        }
    }

    public enum Faction
    {
        Neutral(0),
        Monsters(1),
        Nilfgaard(2),
        NorthernRealms(3),
        Scoiatael(4),
        Skellige(5);

        private int value;
        private Faction(int value){
            this.value=value;
        }

        private static Map map = new HashMap<>();

        static {
            for (Faction faction : Faction.values()) {
                map.put(faction.value, faction);
            }
        }

        public static Faction valueOf(int faction) {
            return (Faction)map.get(faction);
        }

        public int getValue() {
            return value;
        }
    }

    public enum Position
    {
        Melee(0),
        Ranged(1),
        Siege(2),
        Event(3);

        private int value;
        private Position(int value){
            this.value=value;
        }

        private static Map map = new HashMap<>();

        static {
            for (Position position : Position.values()) {
                map.put(position.value, position);
            }
        }

        public static Position valueOf(int position) {
            return (Position)map.get(position);
        }

        public int getValue() {
            return value;
        }
    }

    public enum Loyaute
    {
        Aucune(0),
        Loyal(1),
        Disloyal(2),
        LoyalDisloyal(3);

        private int value;
        private Loyaute(int value){
            this.value=value;
        }

        private static Map map = new HashMap<>();

        static {
            for (Loyaute loyaute : Loyaute.values()) {
                map.put(loyaute.value, loyaute);
            }
        }

        public static Loyaute valueOf(int loyaute) {
            return (Loyaute)map.get(loyaute);
        }

        public int getValue() {
            return value;
        }
    }

    public enum Rarete
    {
        Aucune(0),
        Common(30),
        Rare(80),
        Epic(200),
        Legendary(800);

        private int value;
        private Rarete(int value){
            this.value=value;
        }

        private static Map map = new HashMap<>();

        static {
            for (Rarete rarete : Rarete.values()) {
                map.put(rarete.value, rarete);
            }
        }

        public static Rarete valueOf(int rarete) {
            return (Rarete)map.get(rarete);
        }

        public int getValue() {
            return value;
        }
    }

}
