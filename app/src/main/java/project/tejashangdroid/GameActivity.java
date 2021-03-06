package project.tejashangdroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity
{

    String theWord = "";
    int badLetterCount = 0;
    int goodLetterCount = 0;
    int points=0;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setWord();






    }





    //called when user clicks on button set in activity_game.xml
    public void newLetter(View view)
    {


        //find the text box with a letter in it using the id... then cast it to an EditText object
        EditText editText = (EditText) findViewById(R.id.editTextLetter);




        //set it to my String variable
        String letter = editText.getText().toString();


        //blank out text field for my next guess
        editText.setText("");



        //test I am getting the letter
        Log.d("MYLOG", "The letter is: " + letter);

        //check the length of the letter.
        if(letter.length() == 1)
        {
            checkLetter(letter);
        }
        else //                  (context, text, duration)
        {
            Toast.makeText(this, "Please Enter a Single Letter", Toast.LENGTH_SHORT).show();
        }
    }
    //receives the users letter guess and checks if the letter given exist in the word
    public void checkLetter(String letter)
    {
        //phrase to char for comparison
        char aLetter = letter.charAt(0);
//        if(letter.charAt(0))
//        {
//
//        }

        //tracks if the letter was found in the word
        boolean letterGuessed = false;

        //loop to look for the new letter
        for(int i=0;i<theWord.length();i++)
        {
            if(theWord.charAt(i)==aLetter)
            {
                Log.d("MYLOG", "Letter found " + aLetter);
                letterGuessed=true;
                goodLetterCount++;
                showLetter(i, aLetter);
            }
        }

        if(!letterGuessed)
        {
            Log.d("MYLOG", "Letter Not Found " + aLetter);
            badLetterCount++;
            wrongLetter(Character.toString(aLetter));
        }

        //checks to see if a letter is already used
       letterGuessed = false;





        Log.d("MYLOG", "Check For Win:  " + goodLetterCount + " " + theWord.length());

        if (goodLetterCount == theWord.length())
        {

            points++;
            displayWin();
            //Intent intent = new Intent(this, GameOverActivity.class);
            //startActivity(intent);
        }
        if (goodLetterCount > theWord.length())
        {
            points++;
            displayWin();
        }
    }

    public void wrongLetter(String badLetter)
    {
        TextView textView = (TextView) findViewById(R.id.textViewWrong);



        //phrase to char for comparison
        String previousLetters = textView.getText().toString();

        //tracks if the letter was found in the word
        Log.d("MYLOG", "Bad Letter: " + badLetter + " " + badLetterCount);

        //loop to look for the new letter
        if (badLetterCount > 1)
        {
            textView.setText(previousLetters + " " + badLetter);
        }
        else
        {
            textView.setText(badLetter);
        }

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        if(badLetterCount == 1)
            imageView.setImageResource(R.drawable.hatedroid_1);
        if(badLetterCount == 2)
            imageView.setImageResource(R.drawable.hatedroid_2);
        if(badLetterCount == 3)
            imageView.setImageResource(R.drawable.hatedroid_3);
        if(badLetterCount == 4)
            imageView.setImageResource(R.drawable.hatedroid_4);
        if(badLetterCount == 5)
            imageView.setImageResource(R.drawable.hatedroid_5);
        if(badLetterCount > 5)
        {

//            Intent intent = new Intent(this, GameOverActivity.class);
//            startActivity(intent);
            gameOver();
        }
    }
    //position: position the letter is found
    //letterGuessed: user guessed letter
    //change the display layout with the new letter
    public void showLetter(int position, char letterGuessed)
    {
        //make a reference at the LinearLayout in the activity_game.xml using the R reference
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutLetters);

        //make a reference of the textView of the child within the layout that matches the position of the guessed letter
        TextView textView = (TextView) layout.getChildAt(position);

        //replace the "_" of the view of the guessed letter to the textView
        textView.setText(Character.toString(letterGuessed));
    }



    public void displayWin()
    {
        AlertDialog.Builder winBuild = new AlertDialog.Builder(this);
        winBuild.setTitle("Congratulations");
        winBuild.setMessage("You win! Your scored a point!\nYour points total is: " + points);
        winBuild.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                clearScreen();
                setWord();
            }
        });
       winBuild.show();
    }

    public void gameOver()
    {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra("PointsID", points);
        startActivity(intent);

        clearScreen();
        setWord();
    }

    public void clearScreen()
    {
        TextView textView = (TextView) findViewById(R.id.textViewWrong);
        textView.setText("Guessed Letters");

        badLetterCount = 0;
        goodLetterCount = 0;

        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutLetters);

        for(int i=0;i<layout.getChildCount();i++)
        {
            TextView childViewText = (TextView) layout.getChildAt(i);
            childViewText.setText("_");
        }

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.hangdroid_0);
    }

    private void setWord()
    {
        //words from http://www.litscape.com/words/length/4_letters/4_letter_words.html
        //went to http://www.textfixer.com/tools/uppercase-lowercase-text.php to change lowercase to uppercase
        //this allows android:capitalize="characters" to work
        //runs four letter words
        String words = "AAHS ABET ABLE ABLY ABUT ACED ACES ACHE ACID ACME ACNE ACRE ACTS ADDS ADZE AFAR AFRO AGAR AGED AGES AGOG AGUE AHAS AHEM AHOY AIDE AIDS AILS AIMS AIRS AIRY AJAR AKIN ALAS ALBS ALEF ALES ALGA ALLY ALMS ALOE ALPS ALSO ALTO ALUM AMBO AMEN AMID AMOK AMPS ANAL ANDS ANEW ANON ANTE ANTS ANUS APED APER APES APEX APPS AQUA ARCH ARCS AREA ARES ARIA ARID ARKS ARMS ARMY ARTS ARTY ASHS ASHY ASKS ASPS ATOM ATOP AUNT AURA AUTO AVER AVID AVOW AWAY AWED AWES AWLS AWNS AWNY AWOL AWRY AXED AXEL AXES AXIS AXLE AXON AYES AYIN BAAS BABE BABY BACK BADE BAGS BAHT BAIL BAIT BAKE BALD BALE BALK BALL BALM BAND BANE BANG BANK BANS BARB BARD BARE BARF BARK BARM BARN BARS BASE BASH BASK BASS BATH BATS BAUD BAWD BAWL BAYS BEAD BEAK BEAM BEAN BEAR BEAT BEAU BEDS BEEF BEEN BEEP BEER BEES BEET BEGS BELL BELT BEND BENT BERK BERM BEST BETA BETH BETS BEVY BIAS BIBS BIDE BIDS BIKE BILE BILK BILL BIND BINS BIOS BIRD BIRR BITE BITS BITT BLAB BLAH BLAT BLEB BLED BLEW BLIP BLOB BLOG BLOT BLOW BLUE BLUR BOAR BOAS BOAT BOBS BODE BODY BOGS BOIL BOLD BOLL BOLT BOMB BOND BONE BONK BONY BOOB BOOK BOOM BOON BOOR BOOS BOOT BOPS BORE BORN BOSS BOTH BOTS BOUT BOWL BOWS BOXY BOYS BRAD BRAG BRAN BRAS BRAT BRAY BRED BREW BRIE BRIM BRIS BROW BUBO BUCK BUDS BUFF BUGS BULB BULK BULL BUMP BUMS BUNK BUNS BUNT BUOY BURL BURN BURP BURR BURS BURY BUSH BUSK BUST BUSY BUTS BUTT BUYS BUZZ BYES BYTE CABS CADS CAFE CAGE CAKE CALF CALL CALM CALX CAME CAMP CAMS CANE CANS CANT CAPE CAPS CARD CARE CARP CARS CART CASE CASH CASK CAST CATS CAVE CAWS CECA CEDE CEDI CEES CELL CELT CENT CHAD CHAP CHAR CHAT CHEF CHEW CHIC CHIN CHIP CHIS CHOP CHOW CHUB CHUG CHUM CITE CITY CLAD CLAN CLAP CLAW CLAY CLEF CLIP CLOD CLOG CLOT CLUB CLUE COAL COAT COAX COBS COCK CODA CODE CODS COED COGS COHO COIF COIL COIN COLA COLD COLE COLT COMA COMB COME CONE CONK CONS COOK COOL COOP COOS COOT COPE COPS COPY CORD CORE CORK CORM CORN COST COSY COTS COUP COVE COWL COWS COYS COZY CRAB CRAG CRAM CRAP CREW CRIB CROP CROW CRUD CRUX CUBE CUBS CUDS CUED CUES CUFF CULL CULT CUPS CURB CURD CURE CURL CURS CURT CUSP CUSS CUTE CUTS CWMS CYAN CYST CZAR DABS DADO DADS DAFT DAME DAMN DAMP DAMS DANK DARE DARK DARN DART DASH DATA DATE DAUB DAWN DAYS DAZE DEAD DEAF DEAL DEAN DEAR DEBT DECK DEED DEEM DEEP DEER DEES DEFT DEFY DEIL DELE DELF DELI DELL DEME DEMO DEMY DENE DENS DENT DENY DERE DERM DESK DEVA DEWS DEWY DEYS DHOW DIAL DIBS DICE DIED DIES DIET DIGS DILL DIME DIMS DINE DING DINS DIPS DIRE DIRT DISC DISH DISK DITZ DIVA DIVE DOCK DODO DOER DOES DOFF DOGE DOGS DOLE DOLL DOLT DOME DONE DONS DOOM DOOR DOPE DORK DORM DOSE DOTE DOTH DOTS DOVE DOWN DOZE DOZY DRAB DRAG DRAM DRAW DREG DREW DRIP DROP DRUG DRUM DUAL DUBS DUCK DUCT DUDE DUDS DUEL DUES DUET DUFF DUKE DULL DULY DUMB DUMP DUNE DUNG DUNK DUOS DUPE DUSK DUST DUTY DYED DYER DYES DYNE EACH EARL EARN EARS EASE EAST EASY EATS EAVE EBBS ECHO ECRU EDDY EDGE EDGY EDIT EEKS EELS EELY EERY EFFS EGGS EGOS EKED EKER EKES ELKS ELLS ELMS ELSE EMIR EMIT EMUS ENDS ENOL ENVY EONS EPIC ERAS ERGO EROS ETAS ETCH EURO EVEN EVER EVES EVIL EWER EWES EXAM EXES EXIT EXON EXPO EYED EYES FACE FACT FADE FADS FAIL FAIN FAIR FAKE FALL FAME FANG FANS FARE FARM FAST FATE FATS FAUN FAUX FAVE FAWN FAZE FEAR FEAT FEDS FEED FEEL FEES FEET FELL FELT FEND FENS FERN FETA FEUD FIBS FIGS FILE FILL FILM FIND FINE FINK FINS FIRE FIRM FIRS FISH FIST FITS FIVE FIZZ FLAB FLAG FLAN FLAP FLAT FLAW FLAX FLAY FLEA FLED FLEE FLEW FLEX FLIP FLIT FLOE FLOG FLOP FLOW FLUB FLUE FLUX FOAL FOAM FOBS FOCI FOES FOGS FOGY FOIL FOLD FOLK FOND FONT FOOD FOOL FOOT FORA FORE FORK FORM FORT FOUL FOUR FOWL FOXY FRAY FREE FRET FRIZ FROG FROM FUEL FULL FUME FUMY FUND FUNK FURS FURY FUSE FUSS FUZZ GABS GAFF GAGA GAGE GAGS GAIN GAIT GALA GALE GALL GALS GAME GANG GAPE GAPS GARB GASH GASP GATE GAVE GAWK GAZE GEAR GEEK GEES GELD GELL GELS GEMS GENE GENT GERM GETS GIFT GIGS GILD GILL GILT GIMP GINS GIRD GIRL GIST GIVE GLAD GLEE GLEN GLIA GLIB GLOB GLOW GLUE GLUM GLUT GNAT GNAW GNUS GOAD GOAL GOAT GOBS GODS GOER GOES GOJI GOLD GOLF GONE GONG GOOD GOOF GOON GOOP GOOS GORE GORY GOSH GOTH GOUT GOWN GRAB GRAM GRAY GREW GREY GRID GRIM GRIN GRIP GRIT GROW GRUB GUCK GUFF GULF GULL GULP GUMS GUNK GUNS GURU GUSH GUST GUTS GUYS GYMS GYPS GYRE GYRO HACK HAGS HAIL HAIR HALF HALL HALO HALT HAMS HAND HANG HAPS HARD HARE HARK HARM HARP HASH HASP HATE HATH HATS HAUL HAVE HAWK HAWS HAYS HAZE HAZY HEAD HEAL HEAP HEAR HEAT HECK HEED HEEL HEIR HELD HELL HELM HELP HEME HEMS HENS HERB HERD HERE HERO HERS HETH HEWN HEWS HICK HIDE HIGH HIKE HILL HILT HIND HINT HIPS HIRE HISS HITS HIVE HOAR HOAX HOBO HOED HOER HOES HOGS HOLD HOLE HOLY HOME HONE HONK HOOD HOOF HOOK HOOP HOOT HOPE HOPS HORN HOSE HOST HOTS HOUR HOWL HOWS HUBS HUED HUES HUFF HUGE HUGS HUHS HULA HULK HULL HUMP HUMS HUNG HUNK HUNT HURL HURT HUSH HUSK HUTS HYMN HYPE HYPO IBEX IBIS ICED ICER ICES ICKY ICON IDEA IDES IDLE IDLY IDOL IFFY ILEA ILLS IMAM IMPS INCH INKS INKY INNS INTO IONS IOTA IRED IRES IRIS IRKS IRON ISLE ITCH ITEM JABS JACK JADE JAGS JAIL JAMB JAMS JARS JAVA JAWS JAYS JAZZ JEAN JEEP JEER JELL JERK JEST JETS JIBE JIGS JILT JINK JINX JIVE JOBS JOCK JOGS JOIN JOKE JOLT JOSH JOTS JOWL JOYS JUDO JUGS JUKE JULY JUMP JUNE JUNK JURY JUST JUTE JUTS KALE KAPH KAYS KEEK KEEL KEEN KEEP KEGS KELP KEPT KERN KEYS KICK KIDS KILL KILN KILT KINA KIND KINE KING KINK KIPS KISS KITE KITS KIWI KNEE KNEW KNIT KNOB KNOT KNOW KOOK KUDU KUNA KYAK KYAT LABS LACE LACK LACY LADE LADS LADY LAGS LAID LAIN LAIR LAKE LAMB LAME LAMP LAND LANE LANK LAPS LARD LARI LARK LASH LASS LAST LATE LAUD LAVA LAVE LAWN LAWS LAYS LAZE LAZY LEAD LEAF LEAK LEAN LEAP LEAR LEAS LEEK LEER LEFT LEGS LEKS LEND LENS LENT LESS LEST LETS LEUS LEVS LEVY LEWD LIAR LICE LICK LIDS LIED LIEN LIER LIES LIEU LIFE LIFT LIKE LILY LIMB LIME LIMN LIMO LIMP LIMY LINE LINK LINT LION LIPS LIRA LIRE LISP LIST LITE LIVE LOAD LOAF LOAM LOAN LOBE LOBS LOCH LOCI LOCK LOCO LODE LOFT LOGO LOGS LOIN LOLL LONE LONG LOOK LOOM LOON LOOP LOOS LOOT LOPE LOPS LORD LORE LOSE LOSS LOST LOTI LOTS LOUD LOUT LOVE LOWS LUAU LUBE LUCK LUFF LUGE LUGS LULL LUMP LUNG LURE LURK LUSH LUST LUTE LYNX LYRE MACE MACH MADE MAGE MAGI MAID MAIL MAIM MAIN MAKE MALE MALL MALT MAMA MANE MANS MANY MAPS MARA MARE MARK MARL MARS MART MASH MASK MASS MAST MATE MATH MATS MATT MAUL MAWS MAYO MAYS MAZE MEAD MEAL MEAN MEAT MEEK MEET MELD MELT MEMO MEND MENS MENU MEOW MERE MESA MESH MESS MEWS MICA MICE MIDI MIFF MILD MILE MILK MILL MILS MIME MIND MINE MINI MINK MINT MINX MIRE MISS MIST MITE MITT MOAN MOAT MOBS MOCK MODE MODS MOHO MOLD MOLE MOLT MOMS MONK MOOD MOON MOOR MOOS MOOT MOPE MOPS MORE MOSS MOST MOTH MOVE MOWN MOWS MUCH MUCK MUFF MUGS MULE MULL MUMS MUON MURK MUSE MUSH MUSK MUST MUTE MUTT MYNA MYTH NABS NAGS NAIL NAME NAPE NAPS NAUT NAVE NAVY NAYS NAZI NEAP NEAR NEAT NECK NEED NEON NERD NEST NETS NEVI NEWS NEWT NEXT NIBS NICE NICK NIGH NILS NINE NIPS NITS NOBS NODE NODS NOEL NONE NOON NOPE NORM NOSE NOSY NOTE NOUN NOVA NUDE NUKE NULL NUMB NUNS NUTS OAFS OAKS OARS OATH OATS OBEY OBOE ODDS ODES ODOR OFFS OGLE OGRE OHMS OILS OILY OINK OKAY OKRA OLEO OMEN OMIT OMNI ONCE ONES ONLY ONTO ONUS ONYX OOHS OOID OOPS OOZE OOZY OPAL OPEN OPTS ORAL ORBS ORCA ORES ORYX OUCH OURS OUST OUTS OUZO OVAL OVEN OVER OVUM OWED OWER OWES OWLS OWLY OWNS OXEN OXES PACE PACK PACT PADS PAGE PAID PAIL PAIN PAIR PALE PALL PALM PALS PANE PANG PANS PANT PAPA PARE PARK PARS PART PASS PAST PATE PATH PATS PAVE PAWN PAWS PAYS PEAK PEAL PEAR PEAS PEAT PECK PEEK PEEL PEEP PEER PEGS PELF PELT PEND PENS PENT PEON PEPS PERK PERM PERT PESO PEST PETS PEWS PHIS PICK PIED PIER PIES PIGS PIKE PILE PILI PILL PIMP PINE PING PINK PINS PINT PIPE PIPS PITA PITH PITS PITY PIUS PLAN PLAY PLEA PLED PLOD PLOP PLOT PLOW PLOY PLUG PLUM PLUS POCK PODS POEM POET POGO POKE POKY POLE POLL POLO POMP POND PONY POOH POOL POOP POOR POPE POPS PORE PORK PORN PORT POSE POSH POST POSY POTS POUF POUR POUT POXY PRAM PRAY PREP PREY PRIM PROD PROM PROP PROS PROW PSIS PUBS PUCE PUCK PUFF PUGS PUKE PULL PULP PUMA PUMP PUNK PUNS PUNT PUNY PUPA PUPS PURE PURR PUSH PUTS PUTT PYRE QOPH QUAD QUAY QUID QUIP QUIT QUIZ RACE RACK RACY RAFT RAGE RAGS RAID RAIL RAIN RAKE RAMP RAMS RAND RANG RANK RANT RAPE RAPS RAPT RARE RASH RASP RATE RATS RAVE RAWS RAYS RAZE RAZZ READ REAK REAL REAM REAP REAR REDO REDS REED REEF REEK REEL REFS REIN RELY REND RENT REPO RESH REST REVS RHOS RIAL RIBS RICE RICH RICK RIDE RIDS RIEL RIFE RIFF RIFT RIGS RILE RILL RILY RIME RIMS RIND RING RINK RIOT RIPE RIPS RISE RISK RITE RIVE ROAD ROAM ROAN ROAR ROBE ROBS ROCK RODE RODS ROES ROIL ROLE ROLL ROMP ROOD ROOF ROOK ROOM ROOT ROPE ROPY ROSE ROSY ROTE ROTS ROUE ROUT ROVE ROWS RUBS RUBY RUDE RUED RUES RUFF RUGS RUIN RULE RUMS RUNE RUNG RUNS RUNT RUSE RUSH RUST RUTS SACK SACS SAFE SAGA SAGE SAGS SAGY SAID SAIL SAKE SAKI SALE SALT SAME SAND SANE SANG SANK SAPS SARI SASH SASS SATE SAVE SAWN SAWS SAYS SCAB SCAM SCAN SCAR SCAT SCOT SCUD SCUM SEAL SEAM SEAR SEAS SEAT SECT SEED SEEK SEEM SEEN SEEP SEER SEES SELF SELL SEND SENT SERA SERE SERF SETA SETS SEWN SEWS SEXT SEXY SHAH SHAM SHED SHIM SHIN SHIP SHMO SHOE SHOO SHOP SHOT SHOW SHUN SHUT SICK SIDE SIFT SIGH SIGN SIKH SILK SILL SILO SILT SINE SING SINK SINS SIPS SIRE SIRS SITE SITS SITZ SIZE SKEW SKID SKIM SKIN SKIP SKIS SKIT SLAB SLAM SLAP SLAT SLAW SLAY SLED SLEW SLID SLIM SLIP SLIT SLOB SLOE SLOP SLOT SLOW SLUG SLUM SLUR SMIT SMOG SMUG SMUT SNAG SNAP SNIP SNIT SNOB SNOG SNOT SNOW SNUB SNUG SOAK SOAP SOAR SOBS SOCK SODA SODS SOFA SOFT SOIL SOLD SOLE SOLO SOME SOMS SONG SONS SOON SOOT SOPS SORE SORT SOTS SOUL SOUP SOUR SOWN SOWS SOYA SOYS SPAM SPAN SPAR SPAS SPAT SPAY SPED SPIN SPIT SPOT SPRY SPUD SPUN SPUR STAB STAG STAR STAT STAY STEM STEP STEW STIR STOP STOW STUB STUD STUN STYE STYX SUBS SUCH SUCK SUDS SUED SUER SUES SUET SUIT SULK SUMO SUMP SUMS SUNG SUNK SUNS SURE SURF SWAB SWAG SWAM SWAN SWAP SWAT SWAY SWIG SWIM SWUM SYNC TABS TACK TACO TACT TAGS TAIL TAKA TAKE TALA TALC TALE TALK TALL TAME TAMP TAMS TANK TANS TAPE TAPS TARE TARN TARO TARP TARS TART TASK TAUS TAUT TAXA TAXI TEAK TEAL TEAM TEAR TEAS TECH TEED TEEM TEEN TEES TELL TEND TENS TENT TERM TERN TEST TETH TEXT THAN THAT THAW THEE THEM THEN THEY THIN THIS THOU THRU THUD THUG THUS TICK TICS TIDE TIDY TIED TIER TIES TIFF TIKE TILE TILL TILT TIME TINE TING TINS TINT TINY TIPS TIRE TOAD TOED TOES TOFU TOGA TOIL TOLD TOLL TOMB TOME TONE TONG TONS TOOK TOOL TOOT TOPS TORE TORN TORO TORT TOSS TOTE TOTS TOUR TOUT TOWN TOWS TOYS TRAM TRAP TRAY TREE TREK TRIM TRIO TRIP TROD TROT TROY TRUE TSAR TUBA TUBE TUBS TUCK TUFA TUFF TUFT TUGS TUMS TUNA TUNE TURF TURN TUSK TUTU TWIG TWIN TWIT TWOS TYKE TYPE TYPO TYRO TZAR UGHS UGLY UKES ULNA UMPS UNDO UNIT UNIX UNTO UPON UREA URGE URIC URNS USED USER USES UVEA VAIN VALE VAMP VANE VANG VANS VARY VASE VAST VATS VATU VEAL VEAR VEER VEES VEIL VEIN VEND VENT VERB VERY VEST VETO VETS VIAL VIBE VICE VIED VIES VIEW VILE VINE VISA VISE VOID VOLE VOLT VOTE VOWS VUGS WACK WADE WADS WAFT WAGE WAGS WAIF WAIL WAIT WAKE WALK WALL WAND WANE WANT WARD WARE WARM WARN WARP WARS WART WARY WASH WASP WATT WAVE WAVY WAXY WAYS WEAK WEAN WEAR WEBS WEDS WEED WEEK WEEN WEEP WEIR WELD WELL WELT WEND WENT WEPT WERE WEST WETS WHAM WHAT WHEN WHET WHEW WHEY WHIM WHIP WHIR WHIZ WHOA WHOM WHOP WICK WIDE WIFE WIFI WIGS WILD WILE WILL WILT WILY WIMP WIND WINE WING WINK WINS WIPE WIRE WIRY WISE WISH WISP WIST WITH WITS WIVE WOAD WOES WOKE WOKS WOLF WOMB WONS WONT WOOD WOOF WOOL WOOS WORD WORE WORK WORM WORN WORT WOVE WOWS WRAP WREN WRIT WYES XRAY XYST YACK YAFF YAGI YAKS YALD YAMS YANG YANK YAPS YARD YARE YARN YAUD YAUP YAWL YAWN YAWP YAWS YEAH YEAN YEAR YEAS YEGG YELD YELK YELL YELP YENS YERK YETI YETT YEUK YEWS YILL YINS YIPE YIPS YIRD YIRR YODH YODS YOGA YOGH YOGI YOKE YOLK YOND YONI YORE YOUR YOWE YOWL YOWS YOYO YUAN YUCK YUGA YUKS YULE YURT YUTZ YWIS ZAGS ZANY ZAPS ZARF ZATI ZEAL ZEBU ZEDS ZEES ZEIN ZENS ZERO ZEST ZETA ZIGS ZINC ZINE ZING ZIPS ZITI ZITS ZOEA ZOIC ZONE ZONK ZOOM ZOON ZOOS ZORI ZULU ZYME";
        String [] wordsArray = words.split(" ");
        Log.d("MYLOG", "Number of words:  " + words.length());

        int theNumber = (int) (Math.random() * wordsArray.length);
        Log.d("MYLOG", "Number of words:  " + theNumber);

        theWord = wordsArray[theNumber];
        Log.d("MYLOG", "The Word is:  " + theWord);


    }

    public void onBackPressed()
    {
        AlertDialog.Builder backBuild = new AlertDialog.Builder(this);
        backBuild.setTitle("Back");
        backBuild.setMessage("Do you want to go back to the main menu");
        backBuild.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                nextActivity();
            }
        });
        backBuild.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
               dialog.cancel();
            }
        });
        backBuild.show();

    }

    public void nextActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




}
