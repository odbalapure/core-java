package ourgenericclass;

public class Main {

    public static void main(String[] args) {

        /*FootBallPlayer joe = new FootBallPlayer("Joe");
        BaseBallPlayer pat = new BaseBallPlayer("Pat");
        SoccerPlayer beckham = new SoccerPlayer("Beckham");

        Team adelaideCrows = new Team("Adelaide Crows");
        adelaideCrows.addPlayer(joe);
        adelaideCrows.addPlayer(pat);
        adelaideCrows.addPlayer(beckham);*/

        /*Now the problem here is that we can add any type of player to any team
        One solution to overcome this problem is making separate classes for FootBallPlayer, BaseBallPlayer etc.
        But this will cause code duplication
        Another solution is to use generics*/

        /*Team<FootBallPlayer> adelaideCrows = new Team("Adelaide Crows");
        adelaideCrows.addPlayer(joe);

        Team<BaseBallPlayer> baseBallTeam = new Team<>("Chicago Cubs");
        baseBallTeam.addPlayer(pat);*/

        /*NOTE: Now if you use a String as a Type parameter the code will break with a ClassCastException
        coz we have added a cast in the Team::addPlayer() method
        Solution is using bounded parameters*/

        // Team<String> string = new Team<String>("Broken code");

        /*Team<SoccerPlayer> soccerPlayer = new Team<>("Real Madrid");
        soccerPlayer.addPlayer(beckham);

        Team<FootBallPlayer> melbourne = new Team<>("Melbourne");
        FootBallPlayer banks = new FootBallPlayer("Gordon");
        melbourne.addPlayer(banks);

        Team<FootBallPlayer> hawthorn= new Team<>("Hawthorn");
        Team<FootBallPlayer> fremantle= new Team<>("Fremantle");

        hawthorn.matchResults(fremantle, 1, 0);
        hawthorn.matchResults(adelaideCrows, 3, 8);

        adelaideCrows.matchResults(fremantle, 2, 1);*/

        League<Team<FootBallPlayer>> footBallLeague = new League<>("AFL");
        Team<FootBallPlayer> barcelona = new Team<>("Barcelona");
        Team<FootBallPlayer> realMadrid = new Team<>("Real Madrid");
        Team<FootBallPlayer> chelsey = new Team<>("Chelsey");

        footBallLeague.add(barcelona);
        footBallLeague.add(realMadrid);
        footBallLeague.add(chelsey);

        footBallLeague.showLeagueTable();
    }
}
