﻿var floorLevel = 4;

var rooms={ 
"R1":new r(484+1,91-6,490+1,101-6,0),
"R2":new r(458,91-6,464,101-6,0),
"R3":new r(433-2,91-6,438-2,101-6,0),
"R4":new r(407-2,91-6,412-2,101-6,0),
"R5":new r(381-3,91-6,387-3,101-6,0),
"R6":new r(355-3,91-6,361-4,101-6,0),
"R7":new r(330-4,91-6,335-4,101-6,0),
"R8":new r(304-4,91-6,309-4,101-6,0),
"R9":new r(278-3,91-6,284-4,101-6,0),
"R10":new r(252-7,91-6,258-5,101-6,0),
"R11":new r(227-8,91-6,232-6,101-6,0),
"R12":new r(201-9,91-6,206-7,101-6,0),
"R13":new r(175-9,91-6,181-7,101-6,0),
"R14":new r(149-7,91-6,155-5,101-6,0),
"R15":new r(124-7,91-6,129-5,101-6,0),
"R16":new r(141,165+3,148,175+3,0),
"R17":new r(167,165+3,174,175+3,0),
"R18":new r(193,165+3,200,175+3,0),
"R19":new r(218+1,165+3,226+1,175+3,0),
"R20":new r(244+2,165+3,251+2,175+3,0),
"R21":new r(270+2,165+3,277+2,175+3,0),
"R22":new r(296+2,165+3,303+2,175+3,0),
"R23":new r(321+3,165+3,329+3,175+3,0),
"R24":new r(347+4,165+3,354+4,175+3,0),
"R25":new r(546+1,165+3,554+1,175+3,0),
"R26":new r(572+1,165+3,580+1,175+3,0),
"R27":new r(598+2,165+3,605+2,175+3,0),
"R28":new r(624+2,165+3,631+2,175+3,0),
"R29":new r(649+3,165+3,657+3,175+3,0),
"R30":new r(675+3,165+3,683+3,175+3,0),
"R31":new r(940+3,167+4,948+3,177+4,0),
"R32":new r(968+1,167+4,975+1,177+4,0),
"R33":new r(995+1,167+4,1003+1,177+4,0),
"R34":new r(1023,167+4,1030,177+4,0),
"R35":new r(1101+3,91-6,1109+3,101-6,0),
"R36":new r(1076+2,91-6,1083+2,101-6,0),
"R37":new r(1050+1,91-6,1057+1,101-6,0),
"R38":new r(1024,91-6,1032,101-6,0),
"R39":new r(994+3,91-6,1001+3,101-6,0),
"R40":new r(968+2,91-6,976+2,101-6,0),
"R41":new r(942,91-6,950,101-6,0),
"R42":new r(917-3,91-6,924-3,101-6,0),
"R43":new r(878+2,91-6,886+2,101-6,0),
"R44":new r(813-3,91-6,820-3,101-6,0),
"R45":new r(681,261+6,689,271+6,0),
"R46":new r(655,261+6,663,271+6,0),
"R47":new r(630-1,261+6,638-1,271+6,0),
"R48":new r(603-2,261+6,610-2,271+6,0),
"R49":new r(575-1,261+6,583-1,271+6,0),
"R50":new r(548,261+6,555,271+6,0),
"R51":new r(493-1,261+6,500-1,271+6,0),
"R52":new r(494+2,313+5,502+2,323+5,0),
"R53":new r(528+2,356+2,536+2,366+2,0),
"R54":new r(555+1,356+2,562+1,366+2,0),
"R55":new r(581,356+2,588,366+2,0),
"R56":new r(607,356+2,615,366+2,0),
"R57":new r(633,356+2,641,366+2,0),
"R58":new r(660,356+2,667,366+2,0),
"R59":new r(686-3,356+2,693-3,366+2,0),
"R60":new r(712-5,356+2,720-5,366+2,0),
"R61":new r(738-7,356+2,746-7,366+2,0),
"R62":new r(792,364,799,374,0),
"R63":new r(792,341,799,351,0),
"R64":new r(792,318,799,328,0),
"R65":new r(787+1,261+6,795+1,271+6,0),
"R66":new r(754,261+6,761,271+6,0),
"R67":new r(729-2,261+6,736-2,271+6,0),
"S1":new r(126+3,125,132+3,135,0),
"S2":new r(145+3,125,150+3,135,0),
"S3":new r(163+3,125,168+3,135,0),
"S4":new r(181+3,125,186+3,135,0),
"S5":new r(204-1,125,210-2,135,0),
"S6":new r(222+1,125,228+1,135,0),
"S7":new r(241+2,125,246+2,135,0),
"S8":new r(259+1,125,264+1,135,0),
"S9":new r(277,125,283,135,0),
"S10":new r(290,125,297,135,0),
"S11":new r(324+2,125,332+2,135,0),
"S12":new r(342+3,125,349+3,135,0),
"S13":new r(362+2,125,369+2,135,0),
"S14":new r(378+2,125,386+2,135,0),
"S15":new r(399+1,125,406+1,135,0),
"S16":new r(416+2,125,423+2,135,0),
"S17":new r(439,125,448-1,135,0),
"S18":new r(456,125,463,135,0),
"S19":new r(478,125,486,135,0),
"S21":new r(525,118,532,128,0),
"S22":new r(546+1,118,554+1,128,0),
"S23":new r(569,118,577,128,0),
"S24":new r(525,135+1,532,145+1,0),
"S25":new r(546+1,135+1,554+1,145+1,0),
"S26":new r(569,135+1,577,145+1,0),
"S27":new r(604,118,611,128,0),
"S28":new r(627,118,634,128,0),
"S29":new r(604,135+1,611,145+1,0),
"S30":new r(627,135+1,634,145+1,0),
"S31":new r(665,118,672-1,128,0),
"S32":new r(688,118,695,128,0),
"S33":new r(665,135+1,672,145+1,0),
"S34":new r(688,135+1,695,145+1,0),
"S35":new r(720,118,728,128,0),
"S36":new r(742+1,118,749+1,128,0),
"S37":new r(765+1,118,772+1,128,0),
"S38":new r(720,135+1,728,145+1,0),
"S39":new r(742,135+1,749,145+1,0),
"S40":new r(765,135+1,772,145+1,0),
"S42":new r(818,118,826,128,0),
"S43":new r(738+2,176,745+2,186,0),
"S44":new r(818,135+1,826,145+1,0),
"S45":new r(850,118,857,128,0),
"S46":new r(871,118,879,128,0),
"S47":new r(894,118,902,128,0),
"S48":new r(850,135+1,857,145+1,0),
"S49":new r(871,135+1,879,145+1,0),
"S50":new r(894,135+1,902,145+1,0),
"S57":new r(748,298+4,756,308+4,0),
"S58":new r(727,298+4,734,308+4,0),
"S59":new r(705,298+4,712,308+4,0),
"S60":new r(748,315+4,756,325+4,0),
"S61":new r(727,315+4,734,325+4,0),
"S62":new r(705,315+4,712,325+4,0),
"S63":new r(679-1,298+4,686-1,308+4,0),
"S64":new r(654+1,298+4,661+1,308+4,0),
"S65":new r(629+3,298+4,636+3,308+4,0),
"S66":new r(603+4,298+4,611+4,308+4,0),
"S67":new r(679-1,315+4,686-1,325+4,0),
"S68":new r(654+1,315+4,661+1,325+4,0),
"S69":new r(629+3,315+4,636+3,325+4,0),
"S70":new r(603+4,315+4,611+4,325+4,0),
"S71":new r(585-1,298+4,592-1,308+4,0),
"S72":new r(560,298+4,567,308+4,0),
"S73":new r(535+5,298+4,542+5,308+4,0),
"S74":new r(585-1,315+4,592-1,325+4,0),
"S75":new r(560,315+4,567,325+4,0),
"S76":new r(535+5,315+4,542+5,325+4,0),
"S51":new r(935,125+2,942,135+2,0),
"S52":new r(952,125+2,959,135+2,0),
"S53":new r(974,125+2,981,135+2,0),
"S54":new r(990,125+2,997,135+2,0),
"S55":new r(1009+1,125+2,1017+1,135+2,0),
"S56":new r(1026+1,125+2,1033+1,135+2,0),
"MR4-1":new r(532,91-6,543+2,101-6,0),
"MR4-2":new r(588-3,91-6,600-1,101-6,0),
"MR4-3":new r(694-3,91-6,704,101-6,0),
"MR4-4":new r(745,91-6,756+2,101-6,0),
"MR4-5":new r(838+3,91-6,850+3,101-6,0),
"MR4-6":new r(728-2,220,740-1,230,0)};

var landmarks={"LIFT":new l(421+1,224+1,429+1,234+1,'LIFT','Lift at Level 4'),
"MT1":new l(83,134,83+8.5,134+10,"<img src=\"images/male_toilet.svg\" width=\"16\" height=\"16\"/>","Male Toilet"),
"FT1":new l(83,155,83+8.5,155+10,"<img src=\"images/female_toilet.svg\" width=\"16\" height=\"16\"/>","Female Toilet"),
"MT2":new l(501,212,501+11.5,212+9.5,"<img src=\"images/male_toilet.svg\" width=\"22\" height=\"22\"/>","Male Toilet"),
"FT2":new l(777,212,777+11.5,212+9.5,"<img src=\"images/female_toilet.svg\" width=\"22\" height=\"22\"/>","Female Toilet")};
