package com.example.crazyball.view;

public enum WinningWords {
    val("Way to go!"),
            val1("Super job!"),
            val2("Good for you!"),
            val3("Bright!"),
            val4("Great work!"),
            val5("WOW"),
            val6("Fantastic!"),
            val7("I’m proud of you!"),
            val8("Shine on!"),
            val9("Brilliant!"),
            val0("Excellent!"),
            val10("Bravo!"),
            val11("You are just best!"),
            val12("Super!"),
            val13("Awesome!"),
            val14("Just magically!"),
            val15("Too blissful!"),
            val16("Magically blissful!"),
            val17("Perfect!"),
            val18("Brilliant work!"),
            val19("You are the best!"),
            val20("Super awesome!"),
            val21("The best!"),
            val22("You are a genius!"),
            val23("Awesomness!"),
            val24("Stay in bliss!"),
            val25("Just blissfulness!"),
            val26("Dazzling!"),
            val27("You did it!"),
            val28("Brilliant!"),
            val29("Yes!"),
            val30("Spectacular!"),
            val31("Terrific!"),
            val32("Great!"),
            val33("Mega super!"),
            val34("Extra!"),
            val35("Mega best!"),
            val36("This is just genial!"),
    ;

    private final String winningString;

    WinningWords(String winningString) {
        this.winningString = winningString;
    }

    public String getWinningString() {
        return winningString;
    }
}