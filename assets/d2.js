﻿var floorLevel = 2;

var rooms={ 
"R1":new r(485,94,491,104,0),
"R2":new r(458,94,463,104,0),
"R3":new r(432,94,437,104,0),
"R4":new r(404,94,410,104,0),
"R5":new r(378,94,383,104,0),
"R6":new r(353,94,358,104,0),
"R7":new r(326,94,331,104,0),
"R8":new r(273,94,278,104,0),
"R9":new r(246,94,252,104,0),
"R10":new r(219,94,226,104,0),
"R11":new r(193,94,200,104,0),
"R12":new r(166,94,174,104,0),
"R13":new r(140,94,147,104,0),
"R14":new r(139,166+3,146,176+3,0),
"R15":new r(165,166+3,172,176+3,0),
"R16":new r(192,166+3,200,176+3,0),
"R17":new r(219,166+3,226,176+3,0),
"R18":new r(244,166+3,252,176+3,0),
"R19":new r(272,166+3,279,176+3,0),
"R20":new r(324,166+3,331,176+3,0),
"R21":new r(350,166+3,357,176+3,0),
"R22":new r(801,92,808,102,0),
"R23":new r(829,92,836,102,0),
"R24":new r(856,92,863,102,0),
"R25":new r(883,94,891,104,0),
"R26":new r(916,94,924,104,0),
"R27":new r(948,94,956,104,0),
"R28":new r(980,94,988,104,0),
"R29":new r(1008,94,1015,104,0),
"R30":new r(1040,94,1047,104,0),
"R31":new r(1068,94,1075,104,0),
"R32":new r(1095,94,1102,104,0),
"R33":new r(1016+1,168+2,1024+1,178+2,0),
"R34":new r(988,168+2,995,178+2,0),
"R35":new r(961,168+2,969,178+2,0),
"S1":new r(584,131,590,141,0),
"S2":new r(567,131,572,141,0),
"S3":new r(584,154,589,164,0),
"S4":new r(567,154,572,164,0),
"S5":new r(529,160,535,170,0),
"S6":new r(509,160,514,170,0),
"S7":new r(486,160,492,170,0),
"S8":new r(466,160,471,170,0),
"S9":new r(444,160,449,170,0),
"S10":new r(442,144,449,154,0),
"S11":new r(464,144,471,154,0),
"S12":new r(484,144,492,154,0),
"S13":new r(507,144,514,154,0),
"S14":new r(527,144,535,154,0),
"S15":new r(527,127,535,137,0),
"S16":new r(507,127,514,137,0),
"S17":new r(484,127,492,137,0),
"S18":new r(464,127,471,137,0),
"S19":new r(442,127,449,137,0),
"S20":new r(410,131+1,418,141+1,1),
"S21":new r(397,131+1,404,141+1,1),
"S22":new r(381,131+1,388,141+1,1),
"S23":new r(367,131+1,374,141+1,1),
"S24":new r(351,131+1,359,141+1,1),
"S25":new r(338,131+1,345,141+1,1),
"S26":new r(320,131+1,327,141+1,1),
"S27":new r(306,131+1,314,141+1,1),
"S28":new r(274,131+1,281,141+1,1),
"S29":new r(260,131+1,267,141+1,1),
"S30":new r(243,131+1,251,141+1,1),
"S31":new r(230,131+1,237,141+1,1),
"S32":new r(213,131+1,221,141+1,1),
"S33":new r(200,131+1,207,141+1,1),
"S34":new r(183,131+1,191,141+1,1),
"S35":new r(170,131+1,177,141+1,1),
"S36":new r(154,131+1,161,141+1,1),
"S37":new r(140,131+1,148,141+1,1),
"S38":new r(123,131+1,131,141+1,1),
"S39":new r(702+1,128+2,709+1,138+2,0),
"S40":new r(718+3,128+2,726+3,138+2,0),
"S41":new r(751,128+3,758,138+3,0),
"S42":new r(768+1,128+3,775+1,138+3,0),
"S43":new r(789,128+3,797,138+3,0),
"S44":new r(806+1,128+3,813+1,138+3,0),
"S45":new r(702+1,154,709+1,164,0),
"S46":new r(718+3,154,726+3,164,0),
"S47":new r(751,154,758,164,0),
"S48":new r(768+1,154,775+1,164,0),
"S49":new r(789,154,797,164,0),
"S50":new r(806+1,154,813+1,164,0),
"S51":new r(827,161+1,835,171+1,0),
"S52":new r(849,161+1,856,171+1,0),
"S53":new r(849,145+1,856,155+1,0),
"S54":new r(827,145+1,835,155+1,0),
"S55":new r(827,127+2,835,137+2,0),
"S56":new r(849,127+2,856,137+2,0),
"S57":new r(884,137+1,891,147+1,0),
"S58":new r(905,137+1,912,147+1,0),
"S59":new r(928,137+1,935,147+1,0),
"S60":new r(928,152+3,935,162+3,0),
"S61":new r(905,152+3,912,162+3,0),
"S62":new r(884,152+3,891,162+3,0),
"S67":new r(977,133+3,984,143+3,0),
"S68":new r(999,133+3,1006,143+3,0),
"S69":new r(1018,133+3,1025,143+3,0),
"MR2-3":new r(727+4-6,194+24,735+3,204+24,0),
"MR2-2":new r(757+6-6,94-4,765+5,104-4,0),
"MR2-1":new r(527-4-6,94-4,535-6,104-4,0),
"MR2-?":new r(296+1-3,94,304+2,104,0)};

var landmarks = {"LIFT":new l(421+1,224+1,429+1,234+1,"LIFT","Lift at Level 2"),
"MT1":new l(59,142,59+10,142+10,"<img src=\"images/male_toilet.svg\" width=\"19\" height=\"19\"/>","Male Toilet"),
"FT1":new l(88,142,88+10,142+10,"<img src=\"images/female_toilet.svg\" width=\"19\" height=\"19\"/>","Female Toilet"),
"MT2":new l(779,228,779+11,228+10,"<img src=\"images/male_toilet.svg\" width=\"21\" height=\"21\"/>","Male Toilet"),
"FT2":new l(779,198,779+11,198+10,"<img src=\"images/female_toilet.svg\" width=\"21\" height=\"21\"/>","Female Toilet")};
