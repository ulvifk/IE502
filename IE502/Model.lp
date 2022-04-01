\ LP format - for model browsing. Use MPS format to capture full model detail.
Minimize
  TruckCompletion_10
Subject To
 R0: p_01 = 1
 R1: p_02 = 1
 R2: p_03 = 1
 R3: p_04 = 1
 R4: p_05 = 1
 R5: p_06 = 1
 R6: p_07 = 1
 R7: p_08 = 1
 R8: p_09 = 1
 R9: TruckArrival_0 = 0
 R10: TruckService_0 = 0
 Constraint2_1: x[0][1] + x[2][1] + x[3][1] + x[4][1] + x[5][1] + x[6][1]
   + x[7][1] + x[8][1] + x[9][1] = 1
 Constraint2_2: x[0][2] + x[1][2] + x[3][2] + x[4][2] + x[5][2] + x[6][2]
   + x[7][2] + x[8][2] + x[9][2] = 1
 Constraint2_3: x[0][3] + x[1][3] + x[2][3] + x[4][3] + x[5][3] + x[6][3]
   + x[7][3] + x[8][3] + x[9][3] = 1
 Constraint2_4: x[0][4] + x[1][4] + x[2][4] + x[3][4] + x[5][4] + x[6][4]
   + x[7][4] + x[8][4] + x[9][4] = 1
 Constraint2_5: x[0][5] + x[1][5] + x[2][5] + x[3][5] + x[4][5] + x[6][5]
   + x[7][5] + x[8][5] + x[9][5] = 1
 Constraint2_6: x[0][6] + x[1][6] + x[2][6] + x[3][6] + x[4][6] + x[5][6]
   + x[7][6] + x[8][6] + x[9][6] = 1
 Constraint2_7: x[0][7] + x[1][7] + x[2][7] + x[3][7] + x[4][7] + x[5][7]
   + x[6][7] + x[8][7] + x[9][7] = 1
 Constraint2_8: x[0][8] + x[1][8] + x[2][8] + x[3][8] + x[4][8] + x[5][8]
   + x[6][8] + x[7][8] + x[9][8] = 1
 Constraint2_9: x[0][9] + x[1][9] + x[2][9] + x[3][9] + x[4][9] + x[5][9]
   + x[6][9] + x[7][9] + x[8][9] = 1
 Constraint3: x[0][10] + x[0][1] + x[0][2] + x[0][3] + x[0][4] + x[0][5]
   + x[0][6] + x[0][7] + x[0][8] + x[0][9] = 1
 Constraint4: x[0][10] + x[1][10] + x[2][10] + x[3][10] + x[4][10]
   + x[5][10] + x[6][10] + x[7][10] + x[8][10] + x[9][10] = 1
 Constraint5: x[0][1] - x[1][10] - x[1][2] - x[1][3] - x[1][4] - x[1][5]
   - x[1][6] - x[1][7] - x[1][8] - x[1][9] + x[2][1] + x[3][1] + x[4][1]
   + x[5][1] + x[6][1] + x[7][1] + x[8][1] + x[9][1] = 0
 Constraint5: x[0][2] + x[1][2] - x[2][10] - x[2][1] - x[2][3] - x[2][4]
   - x[2][5] - x[2][6] - x[2][7] - x[2][8] - x[2][9] + x[3][2] + x[4][2]
   + x[5][2] + x[6][2] + x[7][2] + x[8][2] + x[9][2] = 0
 Constraint5: x[0][3] + x[1][3] + x[2][3] - x[3][10] - x[3][1] - x[3][2]
   - x[3][4] - x[3][5] - x[3][6] - x[3][7] - x[3][8] - x[3][9] + x[4][3]
   + x[5][3] + x[6][3] + x[7][3] + x[8][3] + x[9][3] = 0
 Constraint5: x[0][4] + x[1][4] + x[2][4] + x[3][4] - x[4][10] - x[4][1]
   - x[4][2] - x[4][3] - x[4][5] - x[4][6] - x[4][7] - x[4][8] - x[4][9]
   + x[5][4] + x[6][4] + x[7][4] + x[8][4] + x[9][4] = 0
 Constraint5: x[0][5] + x[1][5] + x[2][5] + x[3][5] + x[4][5] - x[5][10]
   - x[5][1] - x[5][2] - x[5][3] - x[5][4] - x[5][6] - x[5][7] - x[5][8]
   - x[5][9] + x[6][5] + x[7][5] + x[8][5] + x[9][5] = 0
 Constraint5: x[0][6] + x[1][6] + x[2][6] + x[3][6] + x[4][6] + x[5][6]
   - x[6][10] - x[6][1] - x[6][2] - x[6][3] - x[6][4] - x[6][5] - x[6][7]
   - x[6][8] - x[6][9] + x[7][6] + x[8][6] + x[9][6] = 0
 Constraint5: x[0][7] + x[1][7] + x[2][7] + x[3][7] + x[4][7] + x[5][7]
   + x[6][7] - x[7][10] - x[7][1] - x[7][2] - x[7][3] - x[7][4] - x[7][5]
   - x[7][6] - x[7][8] - x[7][9] + x[8][7] + x[9][7] = 0
 Constraint5: x[0][8] + x[1][8] + x[2][8] + x[3][8] + x[4][8] + x[5][8]
   + x[6][8] + x[7][8] - x[8][10] - x[8][1] - x[8][2] - x[8][3] - x[8][4]
   - x[8][5] - x[8][6] - x[8][7] - x[8][9] + x[9][8] = 0
 Constraint5: x[0][9] + x[1][9] + x[2][9] + x[3][9] + x[4][9] + x[5][9]
   + x[6][9] + x[7][9] + x[8][9] - x[9][10] - x[9][1] - x[9][2] - x[9][3]
   - x[9][4] - x[9][5] - x[9][6] - x[9][7] - x[9][8] = 0
 Cosntraint11: 11 x[1][10] - u_10 + u_1 <= 10
 Cosntraint11: 11 x[1][2] + u_1 - u_2 <= 10
 Cosntraint11: 11 x[1][3] + u_1 - u_3 <= 10
 Cosntraint11: 11 x[1][4] + u_1 - u_4 <= 10
 Cosntraint11: 11 x[1][5] + u_1 - u_5 <= 10
 Cosntraint11: 11 x[1][6] + u_1 - u_6 <= 10
 Cosntraint11: 11 x[1][7] + u_1 - u_7 <= 10
 Cosntraint11: 11 x[1][8] + u_1 - u_8 <= 10
 Cosntraint11: 11 x[1][9] + u_1 - u_9 <= 10
 Cosntraint11: 11 x[2][10] - u_10 + u_2 <= 10
 Cosntraint11: 11 x[2][1] - u_1 + u_2 <= 10
 Cosntraint11: 11 x[2][3] + u_2 - u_3 <= 10
 Cosntraint11: 11 x[2][4] + u_2 - u_4 <= 10
 Cosntraint11: 11 x[2][5] + u_2 - u_5 <= 10
 Cosntraint11: 11 x[2][6] + u_2 - u_6 <= 10
 Cosntraint11: 11 x[2][7] + u_2 - u_7 <= 10
 Cosntraint11: 11 x[2][8] + u_2 - u_8 <= 10
 Cosntraint11: 11 x[2][9] + u_2 - u_9 <= 10
 Cosntraint11: 11 x[3][10] - u_10 + u_3 <= 10
 Cosntraint11: 11 x[3][1] - u_1 + u_3 <= 10
 Cosntraint11: 11 x[3][2] - u_2 + u_3 <= 10
 Cosntraint11: 11 x[3][4] + u_3 - u_4 <= 10
 Cosntraint11: 11 x[3][5] + u_3 - u_5 <= 10
 Cosntraint11: 11 x[3][6] + u_3 - u_6 <= 10
 Cosntraint11: 11 x[3][7] + u_3 - u_7 <= 10
 Cosntraint11: 11 x[3][8] + u_3 - u_8 <= 10
 Cosntraint11: 11 x[3][9] + u_3 - u_9 <= 10
 Cosntraint11: 11 x[4][10] - u_10 + u_4 <= 10
 Cosntraint11: 11 x[4][1] - u_1 + u_4 <= 10
 Cosntraint11: 11 x[4][2] - u_2 + u_4 <= 10
 Cosntraint11: 11 x[4][3] - u_3 + u_4 <= 10
 Cosntraint11: 11 x[4][5] + u_4 - u_5 <= 10
 Cosntraint11: 11 x[4][6] + u_4 - u_6 <= 10
 Cosntraint11: 11 x[4][7] + u_4 - u_7 <= 10
 Cosntraint11: 11 x[4][8] + u_4 - u_8 <= 10
 Cosntraint11: 11 x[4][9] + u_4 - u_9 <= 10
 Cosntraint11: 11 x[5][10] - u_10 + u_5 <= 10
 Cosntraint11: 11 x[5][1] - u_1 + u_5 <= 10
 Cosntraint11: 11 x[5][2] - u_2 + u_5 <= 10
 Cosntraint11: 11 x[5][3] - u_3 + u_5 <= 10
 Cosntraint11: 11 x[5][4] - u_4 + u_5 <= 10
 Cosntraint11: 11 x[5][6] + u_5 - u_6 <= 10
 Cosntraint11: 11 x[5][7] + u_5 - u_7 <= 10
 Cosntraint11: 11 x[5][8] + u_5 - u_8 <= 10
 Cosntraint11: 11 x[5][9] + u_5 - u_9 <= 10
 Cosntraint11: 11 x[6][10] - u_10 + u_6 <= 10
 Cosntraint11: 11 x[6][1] - u_1 + u_6 <= 10
 Cosntraint11: 11 x[6][2] - u_2 + u_6 <= 10
 Cosntraint11: 11 x[6][3] - u_3 + u_6 <= 10
 Cosntraint11: 11 x[6][4] - u_4 + u_6 <= 10
 Cosntraint11: 11 x[6][5] - u_5 + u_6 <= 10
 Cosntraint11: 11 x[6][7] + u_6 - u_7 <= 10
 Cosntraint11: 11 x[6][8] + u_6 - u_8 <= 10
 Cosntraint11: 11 x[6][9] + u_6 - u_9 <= 10
 Cosntraint11: 11 x[7][10] - u_10 + u_7 <= 10
 Cosntraint11: 11 x[7][1] - u_1 + u_7 <= 10
 Cosntraint11: 11 x[7][2] - u_2 + u_7 <= 10
 Cosntraint11: 11 x[7][3] - u_3 + u_7 <= 10
 Cosntraint11: 11 x[7][4] - u_4 + u_7 <= 10
 Cosntraint11: 11 x[7][5] - u_5 + u_7 <= 10
 Cosntraint11: 11 x[7][6] - u_6 + u_7 <= 10
 Cosntraint11: 11 x[7][8] + u_7 - u_8 <= 10
 Cosntraint11: 11 x[7][9] + u_7 - u_9 <= 10
 Cosntraint11: 11 x[8][10] - u_10 + u_8 <= 10
 Cosntraint11: 11 x[8][1] - u_1 + u_8 <= 10
 Cosntraint11: 11 x[8][2] - u_2 + u_8 <= 10
 Cosntraint11: 11 x[8][3] - u_3 + u_8 <= 10
 Cosntraint11: 11 x[8][4] - u_4 + u_8 <= 10
 Cosntraint11: 11 x[8][5] - u_5 + u_8 <= 10
 Cosntraint11: 11 x[8][6] - u_6 + u_8 <= 10
 Cosntraint11: 11 x[8][7] - u_7 + u_8 <= 10
 Cosntraint11: 11 x[8][9] + u_8 - u_9 <= 10
 Cosntraint11: 11 x[9][10] - u_10 + u_9 <= 10
 Cosntraint11: 11 x[9][1] - u_1 + u_9 <= 10
 Cosntraint11: 11 x[9][2] - u_2 + u_9 <= 10
 Cosntraint11: 11 x[9][3] - u_3 + u_9 <= 10
 Cosntraint11: 11 x[9][4] - u_4 + u_9 <= 10
 Cosntraint11: 11 x[9][5] - u_5 + u_9 <= 10
 Cosntraint11: 11 x[9][6] - u_6 + u_9 <= 10
 Cosntraint11: 11 x[9][7] - u_7 + u_9 <= 10
 Cosntraint11: 11 x[9][8] - u_8 + u_9 <= 10
 Constraint12: 11 p_12 + u_1 - u_2 >= 1
 Constraint12: 11 p_13 + u_1 - u_3 >= 1
 Constraint12: 11 p_14 + u_1 - u_4 >= 1
 Constraint12: 11 p_15 + u_1 - u_5 >= 1
 Constraint12: 11 p_16 + u_1 - u_6 >= 1
 Constraint12: 11 p_17 + u_1 - u_7 >= 1
 Constraint12: 11 p_18 + u_1 - u_8 >= 1
 Constraint12: 11 p_19 + u_1 - u_9 >= 1
 Constraint12: 11 p_21 - u_1 + u_2 >= 1
 Constraint12: 11 p_23 + u_2 - u_3 >= 1
 Constraint12: 11 p_24 + u_2 - u_4 >= 1
 Constraint12: 11 p_25 + u_2 - u_5 >= 1
 Constraint12: 11 p_26 + u_2 - u_6 >= 1
 Constraint12: 11 p_27 + u_2 - u_7 >= 1
 Constraint12: 11 p_28 + u_2 - u_8 >= 1
 Constraint12: 11 p_29 + u_2 - u_9 >= 1
 Constraint12: 11 p_31 - u_1 + u_3 >= 1
 Constraint12: 11 p_32 - u_2 + u_3 >= 1
 Constraint12: 11 p_34 + u_3 - u_4 >= 1
 Constraint12: 11 p_35 + u_3 - u_5 >= 1
 Constraint12: 11 p_36 + u_3 - u_6 >= 1
 Constraint12: 11 p_37 + u_3 - u_7 >= 1
 Constraint12: 11 p_38 + u_3 - u_8 >= 1
 Constraint12: 11 p_39 + u_3 - u_9 >= 1
 Constraint12: 11 p_41 - u_1 + u_4 >= 1
 Constraint12: 11 p_42 - u_2 + u_4 >= 1
 Constraint12: 11 p_43 - u_3 + u_4 >= 1
 Constraint12: 11 p_45 + u_4 - u_5 >= 1
 Constraint12: 11 p_46 + u_4 - u_6 >= 1
 Constraint12: 11 p_47 + u_4 - u_7 >= 1
 Constraint12: 11 p_48 + u_4 - u_8 >= 1
 Constraint12: 11 p_49 + u_4 - u_9 >= 1
 Constraint12: 11 p_51 - u_1 + u_5 >= 1
 Constraint12: 11 p_52 - u_2 + u_5 >= 1
 Constraint12: 11 p_53 - u_3 + u_5 >= 1
 Constraint12: 11 p_54 - u_4 + u_5 >= 1
 Constraint12: 11 p_56 + u_5 - u_6 >= 1
 Constraint12: 11 p_57 + u_5 - u_7 >= 1
 Constraint12: 11 p_58 + u_5 - u_8 >= 1
 Constraint12: 11 p_59 + u_5 - u_9 >= 1
 Constraint12: 11 p_61 - u_1 + u_6 >= 1
 Constraint12: 11 p_62 - u_2 + u_6 >= 1
 Constraint12: 11 p_63 - u_3 + u_6 >= 1
 Constraint12: 11 p_64 - u_4 + u_6 >= 1
 Constraint12: 11 p_65 - u_5 + u_6 >= 1
 Constraint12: 11 p_67 + u_6 - u_7 >= 1
 Constraint12: 11 p_68 + u_6 - u_8 >= 1
 Constraint12: 11 p_69 + u_6 - u_9 >= 1
 Constraint12: 11 p_71 - u_1 + u_7 >= 1
 Constraint12: 11 p_72 - u_2 + u_7 >= 1
 Constraint12: 11 p_73 - u_3 + u_7 >= 1
 Constraint12: 11 p_74 - u_4 + u_7 >= 1
 Constraint12: 11 p_75 - u_5 + u_7 >= 1
 Constraint12: 11 p_76 - u_6 + u_7 >= 1
 Constraint12: 11 p_78 + u_7 - u_8 >= 1
 Constraint12: 11 p_79 + u_7 - u_9 >= 1
 Constraint12: 11 p_81 - u_1 + u_8 >= 1
 Constraint12: 11 p_82 - u_2 + u_8 >= 1
 Constraint12: 11 p_83 - u_3 + u_8 >= 1
 Constraint12: 11 p_84 - u_4 + u_8 >= 1
 Constraint12: 11 p_85 - u_5 + u_8 >= 1
 Constraint12: 11 p_86 - u_6 + u_8 >= 1
 Constraint12: 11 p_87 - u_7 + u_8 >= 1
 Constraint12: 11 p_89 + u_8 - u_9 >= 1
 Constraint12: 11 p_91 - u_1 + u_9 >= 1
 Constraint12: 11 p_92 - u_2 + u_9 >= 1
 Constraint12: 11 p_93 - u_3 + u_9 >= 1
 Constraint12: 11 p_94 - u_4 + u_9 >= 1
 Constraint12: 11 p_95 - u_5 + u_9 >= 1
 Constraint12: 11 p_96 - u_6 + u_9 >= 1
 Constraint12: 11 p_97 - u_7 + u_9 >= 1
 Constraint12: 11 p_98 - u_8 + u_9 >= 1
 Constraint13: 11 p_12 + u_1 - u_2 <= 10
 Constraint13: 11 p_13 + u_1 - u_3 <= 10
 Constraint13: 11 p_14 + u_1 - u_4 <= 10
 Constraint13: 11 p_15 + u_1 - u_5 <= 10
 Constraint13: 11 p_16 + u_1 - u_6 <= 10
 Constraint13: 11 p_17 + u_1 - u_7 <= 10
 Constraint13: 11 p_18 + u_1 - u_8 <= 10
 Constraint13: 11 p_19 + u_1 - u_9 <= 10
 Constraint13: 11 p_21 - u_1 + u_2 <= 10
 Constraint13: 11 p_23 + u_2 - u_3 <= 10
 Constraint13: 11 p_24 + u_2 - u_4 <= 10
 Constraint13: 11 p_25 + u_2 - u_5 <= 10
 Constraint13: 11 p_26 + u_2 - u_6 <= 10
 Constraint13: 11 p_27 + u_2 - u_7 <= 10
 Constraint13: 11 p_28 + u_2 - u_8 <= 10
 Constraint13: 11 p_29 + u_2 - u_9 <= 10
 Constraint13: 11 p_31 - u_1 + u_3 <= 10
 Constraint13: 11 p_32 - u_2 + u_3 <= 10
 Constraint13: 11 p_34 + u_3 - u_4 <= 10
 Constraint13: 11 p_35 + u_3 - u_5 <= 10
 Constraint13: 11 p_36 + u_3 - u_6 <= 10
 Constraint13: 11 p_37 + u_3 - u_7 <= 10
 Constraint13: 11 p_38 + u_3 - u_8 <= 10
 Constraint13: 11 p_39 + u_3 - u_9 <= 10
 Constraint13: 11 p_41 - u_1 + u_4 <= 10
 Constraint13: 11 p_42 - u_2 + u_4 <= 10
 Constraint13: 11 p_43 - u_3 + u_4 <= 10
 Constraint13: 11 p_45 + u_4 - u_5 <= 10
 Constraint13: 11 p_46 + u_4 - u_6 <= 10
 Constraint13: 11 p_47 + u_4 - u_7 <= 10
 Constraint13: 11 p_48 + u_4 - u_8 <= 10
 Constraint13: 11 p_49 + u_4 - u_9 <= 10
 Constraint13: 11 p_51 - u_1 + u_5 <= 10
 Constraint13: 11 p_52 - u_2 + u_5 <= 10
 Constraint13: 11 p_53 - u_3 + u_5 <= 10
 Constraint13: 11 p_54 - u_4 + u_5 <= 10
 Constraint13: 11 p_56 + u_5 - u_6 <= 10
 Constraint13: 11 p_57 + u_5 - u_7 <= 10
 Constraint13: 11 p_58 + u_5 - u_8 <= 10
 Constraint13: 11 p_59 + u_5 - u_9 <= 10
 Constraint13: 11 p_61 - u_1 + u_6 <= 10
 Constraint13: 11 p_62 - u_2 + u_6 <= 10
 Constraint13: 11 p_63 - u_3 + u_6 <= 10
 Constraint13: 11 p_64 - u_4 + u_6 <= 10
 Constraint13: 11 p_65 - u_5 + u_6 <= 10
 Constraint13: 11 p_67 + u_6 - u_7 <= 10
 Constraint13: 11 p_68 + u_6 - u_8 <= 10
 Constraint13: 11 p_69 + u_6 - u_9 <= 10
 Constraint13: 11 p_71 - u_1 + u_7 <= 10
 Constraint13: 11 p_72 - u_2 + u_7 <= 10
 Constraint13: 11 p_73 - u_3 + u_7 <= 10
 Constraint13: 11 p_74 - u_4 + u_7 <= 10
 Constraint13: 11 p_75 - u_5 + u_7 <= 10
 Constraint13: 11 p_76 - u_6 + u_7 <= 10
 Constraint13: 11 p_78 + u_7 - u_8 <= 10
 Constraint13: 11 p_79 + u_7 - u_9 <= 10
 Constraint13: 11 p_81 - u_1 + u_8 <= 10
 Constraint13: 11 p_82 - u_2 + u_8 <= 10
 Constraint13: 11 p_83 - u_3 + u_8 <= 10
 Constraint13: 11 p_84 - u_4 + u_8 <= 10
 Constraint13: 11 p_85 - u_5 + u_8 <= 10
 Constraint13: 11 p_86 - u_6 + u_8 <= 10
 Constraint13: 11 p_87 - u_7 + u_8 <= 10
 Constraint13: 11 p_89 + u_8 - u_9 <= 10
 Constraint13: 11 p_91 - u_1 + u_9 <= 10
 Constraint13: 11 p_92 - u_2 + u_9 <= 10
 Constraint13: 11 p_93 - u_3 + u_9 <= 10
 Constraint13: 11 p_94 - u_4 + u_9 <= 10
 Constraint13: 11 p_95 - u_5 + u_9 <= 10
 Constraint13: 11 p_96 - u_6 + u_9 <= 10
 Constraint13: 11 p_97 - u_7 + u_9 <= 10
 Constraint13: 11 p_98 - u_8 + u_9 <= 10
 Constraint14: p_12 + p_21 = 1
 Constraint14: p_13 + p_31 = 1
 Constraint14: p_14 + p_41 = 1
 Constraint14: p_15 + p_51 = 1
 Constraint14: p_16 + p_61 = 1
 Constraint14: p_17 + p_71 = 1
 Constraint14: p_18 + p_81 = 1
 Constraint14: p_19 + p_91 = 1
 Constraint14: p_12 + p_21 = 1
 Constraint14: p_23 + p_32 = 1
 Constraint14: p_24 + p_42 = 1
 Constraint14: p_25 + p_52 = 1
 Constraint14: p_26 + p_62 = 1
 Constraint14: p_27 + p_72 = 1
 Constraint14: p_28 + p_82 = 1
 Constraint14: p_29 + p_92 = 1
 Constraint14: p_13 + p_31 = 1
 Constraint14: p_23 + p_32 = 1
 Constraint14: p_34 + p_43 = 1
 Constraint14: p_35 + p_53 = 1
 Constraint14: p_36 + p_63 = 1
 Constraint14: p_37 + p_73 = 1
 Constraint14: p_38 + p_83 = 1
 Constraint14: p_39 + p_93 = 1
 Constraint14: p_14 + p_41 = 1
 Constraint14: p_24 + p_42 = 1
 Constraint14: p_34 + p_43 = 1
 Constraint14: p_45 + p_54 = 1
 Constraint14: p_46 + p_64 = 1
 Constraint14: p_47 + p_74 = 1
 Constraint14: p_48 + p_84 = 1
 Constraint14: p_49 + p_94 = 1
 Constraint14: p_15 + p_51 = 1
 Constraint14: p_25 + p_52 = 1
 Constraint14: p_35 + p_53 = 1
 Constraint14: p_45 + p_54 = 1
 Constraint14: p_56 + p_65 = 1
 Constraint14: p_57 + p_75 = 1
 Constraint14: p_58 + p_85 = 1
 Constraint14: p_59 + p_95 = 1
 Constraint14: p_16 + p_61 = 1
 Constraint14: p_26 + p_62 = 1
 Constraint14: p_36 + p_63 = 1
 Constraint14: p_46 + p_64 = 1
 Constraint14: p_56 + p_65 = 1
 Constraint14: p_67 + p_76 = 1
 Constraint14: p_68 + p_86 = 1
 Constraint14: p_69 + p_96 = 1
 Constraint14: p_17 + p_71 = 1
 Constraint14: p_27 + p_72 = 1
 Constraint14: p_37 + p_73 = 1
 Constraint14: p_47 + p_74 = 1
 Constraint14: p_57 + p_75 = 1
 Constraint14: p_67 + p_76 = 1
 Constraint14: p_78 + p_87 = 1
 Constraint14: p_79 + p_97 = 1
 Constraint14: p_18 + p_81 = 1
 Constraint14: p_28 + p_82 = 1
 Constraint14: p_38 + p_83 = 1
 Constraint14: p_48 + p_84 = 1
 Constraint14: p_58 + p_85 = 1
 Constraint14: p_68 + p_86 = 1
 Constraint14: p_78 + p_87 = 1
 Constraint14: p_89 + p_98 = 1
 Constraint14: p_19 + p_91 = 1
 Constraint14: p_29 + p_92 = 1
 Constraint14: p_39 + p_93 = 1
 Constraint14: p_49 + p_94 = 1
 Constraint14: p_59 + p_95 = 1
 Constraint14: p_69 + p_96 = 1
 Constraint14: p_79 + p_97 = 1
 Constraint14: p_89 + p_98 = 1
 Constraint31_[0][10]: - 42255.49000000002 x[0][10] + TruckArrival_10
   - TruckCompletion_0 >= -42255.49000000002
 Constraint31_[0][1]: - 42255.49000000002 x[0][1] + TruckArrival_1
   - TruckCompletion_0 >= -41580.41000000002
 Constraint31_[0][2]: - 42255.49000000002 x[0][2] + TruckArrival_2
   - TruckCompletion_0 >= -40196.57000000002
 Constraint31_[0][3]: - 42255.49000000002 x[0][3] + TruckArrival_3
   - TruckCompletion_0 >= -40670
 Constraint31_[0][4]: - 42255.49000000002 x[0][4] + TruckArrival_4
   - TruckCompletion_0 >= -39789.57000000002
 Constraint31_[0][5]: - 42255.49000000002 x[0][5] + TruckArrival_5
   - TruckCompletion_0 >= -39535.92000000002
 Constraint31_[0][6]: - 42255.49000000002 x[0][6] + TruckArrival_6
   - TruckCompletion_0 >= -39907.99000000002
 Constraint31_[0][7]: - 42255.49000000002 x[0][7] + TruckArrival_7
   - TruckCompletion_0 >= -40455.82000000002
 Constraint31_[0][8]: - 42255.49000000002 x[0][8] + TruckArrival_8
   - TruckCompletion_0 >= -41162.92000000002
 Constraint31_[0][9]: - 42255.49000000002 x[0][9] + TruckArrival_9
   - TruckCompletion_0 >= -41176.95000000002
 Constraint31_[1][10]: - 42255.49000000002 x[1][10] + TruckArrival_10
   - TruckCompletion_1 >= -41621.04000000002
 Constraint31_[1][2]: - 42255.49000000002 x[1][2] + TruckArrival_2
   - TruckCompletion_1 >= -40178.8
 Constraint31_[1][3]: - 42255.49000000002 x[1][3] + TruckArrival_3
   - TruckCompletion_1 >= -40537.61000000002
 Constraint31_[1][4]: - 42255.49000000002 x[1][4] + TruckArrival_4
   - TruckCompletion_1 >= -39657.18000000002
 Constraint31_[1][5]: - 42255.49000000002 x[1][5] + TruckArrival_5
   - TruckCompletion_1 >= -39356.14000000002
 Constraint31_[1][6]: - 42255.49000000002 x[1][6] + TruckArrival_6
   - TruckCompletion_1 >= -39816.13000000002
 Constraint31_[1][7]: - 42255.49000000002 x[1][7] + TruckArrival_7
   - TruckCompletion_1 >= -40354.18000000002
 Constraint31_[1][8]: - 42255.49000000002 x[1][8] + TruckArrival_8
   - TruckCompletion_1 >= -41189.76000000002
 Constraint31_[1][9]: - 42255.49000000002 x[1][9] + TruckArrival_9
   - TruckCompletion_1 >= -41203.8
 Constraint31_[2][10]: - 42255.49000000002 x[2][10] + TruckArrival_10
   - TruckCompletion_2 >= -40308.57000000002
 Constraint31_[2][1]: - 42255.49000000002 x[2][1] + TruckArrival_1
   - TruckCompletion_2 >= -40424.4
 Constraint31_[2][3]: - 42255.49000000002 x[2][3] + TruckArrival_3
   - TruckCompletion_2 >= -41496.61000000002
 Constraint31_[2][4]: - 42255.49000000002 x[2][4] + TruckArrival_4
   - TruckCompletion_2 >= -40960.81000000002
 Constraint31_[2][5]: - 42255.49000000002 x[2][5] + TruckArrival_5
   - TruckCompletion_2 >= -38705.02000000002
 Constraint31_[2][6]: - 42255.49000000002 x[2][6] + TruckArrival_6
   - TruckCompletion_2 >= -41467.52000000002
 Constraint31_[2][7]: - 42255.49000000002 x[2][7] + TruckArrival_7
   - TruckCompletion_2 >= -41490.73000000002
 Constraint31_[2][8]: - 42255.49000000002 x[2][8] + TruckArrival_8
   - TruckCompletion_2 >= -40091.06000000002
 Constraint31_[2][9]: - 42255.49000000002 x[2][9] + TruckArrival_9
   - TruckCompletion_2 >= -40105.1
 Constraint31_[3][10]: - 42255.49000000002 x[3][10] + TruckArrival_10
   - TruckCompletion_3 >= -40556.86000000002
 Constraint31_[3][1]: - 42255.49000000002 x[3][1] + TruckArrival_1
   - TruckCompletion_3 >= -40692.49000000002
 Constraint31_[3][2]: - 42255.49000000002 x[3][2] + TruckArrival_2
   - TruckCompletion_3 >= -41481.05000000002
 Constraint31_[3][4]: - 42255.49000000002 x[3][4] + TruckArrival_4
   - TruckCompletion_3 >= -41118.13000000002
 Constraint31_[3][5]: - 42255.49000000002 x[3][5] + TruckArrival_5
   - TruckCompletion_3 >= -39013.04000000002
 Constraint31_[3][6]: - 42255.49000000002 x[3][6] + TruckArrival_6
   - TruckCompletion_3 >= -41236.55000000002
 Constraint31_[3][7]: - 42255.49000000002 x[3][7] + TruckArrival_7
   - TruckCompletion_3 >= -42041.31000000002
 Constraint31_[3][8]: - 42255.49000000002 x[3][8] + TruckArrival_8
   - TruckCompletion_3 >= -40359.16000000002
 Constraint31_[3][9]: - 42255.49000000002 x[3][9] + TruckArrival_9
   - TruckCompletion_3 >= -40373.19000000002
 Constraint31_[4][10]: - 42255.49000000002 x[4][10] + TruckArrival_10
   - TruckCompletion_4 >= -39790.4
 Constraint31_[4][1]: - 42255.49000000002 x[4][1] + TruckArrival_1
   - TruckCompletion_4 >= -39909.7
 Constraint31_[4][2]: - 42255.49000000002 x[4][2] + TruckArrival_2
   - TruckCompletion_4 >= -40827.27000000002
 Constraint31_[4][3]: - 42255.49000000002 x[4][3] + TruckArrival_3
   - TruckCompletion_4 >= -41135.22000000002
 Constraint31_[4][5]: - 42255.49000000002 x[4][5] + TruckArrival_5
   - TruckCompletion_4 >= -38249.18000000002
 Constraint31_[4][6]: - 42255.49000000002 x[4][6] + TruckArrival_6
   - TruckCompletion_4 >= -40893.91000000002
 Constraint31_[4][7]: - 42255.49000000002 x[4][7] + TruckArrival_7
   - TruckCompletion_4 >= -41163.38000000002
 Constraint31_[4][8]: - 42255.49000000002 x[4][8] + TruckArrival_8
   - TruckCompletion_4 >= -39576.37000000002
 Constraint31_[4][9]: - 42255.49000000002 x[4][9] + TruckArrival_9
   - TruckCompletion_4 >= -39590.4
 Constraint31_[5][10]: - 42255.49000000002 x[5][10] + TruckArrival_10
   - TruckCompletion_5 >= -39655.13000000002
 Constraint31_[5][1]: - 42255.49000000002 x[5][1] + TruckArrival_1
   - TruckCompletion_5 >= -39405.99000000002
 Constraint31_[5][2]: - 42255.49000000002 x[5][2] + TruckArrival_2
   - TruckCompletion_5 >= -38523.44000000002
 Constraint31_[5][3]: - 42255.49000000002 x[5][3] + TruckArrival_3
   - TruckCompletion_5 >= -38996.87000000002
 Constraint31_[5][4]: - 42255.49000000002 x[5][4] + TruckArrival_4
   - TruckCompletion_5 >= -38116.44000000002
 Constraint31_[5][6]: - 42255.49000000002 x[5][6] + TruckArrival_6
   - TruckCompletion_5 >= -38234.86000000002
 Constraint31_[5][7]: - 42255.49000000002 x[5][7] + TruckArrival_7
   - TruckCompletion_5 >= -38782.69000000002
 Constraint31_[5][8]: - 42255.49000000002 x[5][8] + TruckArrival_8
   - TruckCompletion_5 >= -39040.72000000002
 Constraint31_[5][9]: - 42255.49000000002 x[5][9] + TruckArrival_9
   - TruckCompletion_5 >= -39054.75000000002
 Constraint31_[6][10]: - 42255.49000000002 x[6][10] + TruckArrival_10
   - TruckCompletion_6 >= -39899.92000000002
 Constraint31_[6][1]: - 42255.49000000002 x[6][1] + TruckArrival_1
   - TruckCompletion_6 >= -40003.91000000002
 Constraint31_[6][2]: - 42255.49000000002 x[6][2] + TruckArrival_2
   - TruckCompletion_6 >= -41439.39000000002
 Constraint31_[6][3]: - 42255.49000000002 x[6][3] + TruckArrival_3
   - TruckCompletion_6 >= -41158.26000000002
 Constraint31_[6][4]: - 42255.49000000002 x[6][4] + TruckArrival_4
   - TruckCompletion_6 >= -41002.85000000002
 Constraint31_[6][5]: - 42255.49000000002 x[6][5] + TruckArrival_5
   - TruckCompletion_6 >= -38272.23000000002
 Constraint31_[6][7]: - 42255.49000000002 x[6][7] + TruckArrival_7
   - TruckCompletion_6 >= -41080.67000000002
 Constraint31_[6][8]: - 42255.49000000002 x[6][8] + TruckArrival_8
   - TruckCompletion_6 >= -39612.01000000002
 Constraint31_[6][9]: - 42255.49000000002 x[6][9] + TruckArrival_9
   - TruckCompletion_6 >= -39626.05000000002
 Constraint31_[7][10]: - 42255.49000000002 x[7][10] + TruckArrival_10
   - TruckCompletion_7 >= -40534.44000000002
 Constraint31_[7][1]: - 42255.49000000002 x[7][1] + TruckArrival_1
   - TruckCompletion_7 >= -40670.07000000002
 Constraint31_[7][2]: - 42255.49000000002 x[7][2] + TruckArrival_2
   - TruckCompletion_7 >= -41489.35000000002
 Constraint31_[7][3]: - 42255.49000000002 x[7][3] + TruckArrival_3
   - TruckCompletion_7 >= -42023.35000000002
 Constraint31_[7][4]: - 42255.49000000002 x[7][4] + TruckArrival_4
   - TruckCompletion_7 >= -41038.77000000002
 Constraint31_[7][5]: - 42255.49000000002 x[7][5] + TruckArrival_5
   - TruckCompletion_7 >= -38950.7
 Constraint31_[7][6]: - 42255.49000000002 x[7][6] + TruckArrival_6
   - TruckCompletion_7 >= -41135.93000000002
 Constraint31_[7][8]: - 42255.49000000002 x[7][8] + TruckArrival_8
   - TruckCompletion_7 >= -40336.74000000002
 Constraint31_[7][9]: - 42255.49000000002 x[7][9] + TruckArrival_9
   - TruckCompletion_7 >= -40350.78000000002
 Constraint31_[8][10]: - 42255.49000000002 x[8][10] + TruckArrival_10
   - TruckCompletion_8 >= -40969.23000000002
 Constraint31_[8][1]: - 42255.49000000002 x[8][1] + TruckArrival_1
   - TruckCompletion_8 >= -41044.6
 Constraint31_[8][2]: - 42255.49000000002 x[8][2] + TruckArrival_2
   - TruckCompletion_8 >= -39858.35000000002
 Constraint31_[8][3]: - 42255.49000000002 x[8][3] + TruckArrival_3
   - TruckCompletion_8 >= -40082.7
 Constraint31_[8][4]: - 42255.49000000002 x[8][4] + TruckArrival_4
   - TruckCompletion_8 >= -39282.26000000002
 Constraint31_[8][5]: - 42255.49000000002 x[8][5] + TruckArrival_5
   - TruckCompletion_8 >= -38846.65000000002
 Constraint31_[8][6]: - 42255.49000000002 x[8][6] + TruckArrival_6
   - TruckCompletion_8 >= -39476.91000000002
 Constraint31_[8][7]: - 42255.49000000002 x[8][7] + TruckArrival_7
   - TruckCompletion_8 >= -40033.72000000002
 Constraint31_[8][9]: - 42255.49000000002 x[8][9] + TruckArrival_9
   - TruckCompletion_8 >= -42210.97000000002
 Constraint31_[9][10]: - 42255.49000000002 x[9][10] + TruckArrival_10
   - TruckCompletion_9 >= -40993.03000000002
 Constraint31_[9][1]: - 42255.49000000002 x[9][1] + TruckArrival_1
   - TruckCompletion_9 >= -41043.4
 Constraint31_[9][2]: - 42255.49000000002 x[9][2] + TruckArrival_2
   - TruckCompletion_9 >= -39857.15000000002
 Constraint31_[9][3]: - 42255.49000000002 x[9][3] + TruckArrival_3
   - TruckCompletion_9 >= -40081.49000000002
 Constraint31_[9][4]: - 42255.49000000002 x[9][4] + TruckArrival_4
   - TruckCompletion_9 >= -39281.06000000002
 Constraint31_[9][5]: - 42255.49000000002 x[9][5] + TruckArrival_5
   - TruckCompletion_9 >= -38845.45000000002
 Constraint31_[9][6]: - 42255.49000000002 x[9][6] + TruckArrival_6
   - TruckCompletion_9 >= -39475.71000000002
 Constraint31_[9][7]: - 42255.49000000002 x[9][7] + TruckArrival_7
   - TruckCompletion_9 >= -40032.52000000002
 Constraint31_[9][8]: - 42255.49000000002 x[9][8] + TruckArrival_8
   - TruckCompletion_9 >= -42210.97000000002
 Constraint32: - TruckArrival_10 + TruckService_10 >= 0
 Constraint32: - 30 x[0][1] - 30 x[2][1] - 30 x[3][1] - 30 x[4][1]
   - 30 x[5][1] - 30 x[6][1] - 30 x[7][1] - 30 x[8][1] - 30 x[9][1]
   - TruckArrival_1 + TruckService_1 >= 0
 Constraint32: - 30 x[0][2] - 30 x[1][2] - 30 x[3][2] - 30 x[4][2]
   - 30 x[5][2] - 30 x[6][2] - 30 x[7][2] - 30 x[8][2] - 30 x[9][2]
   - TruckArrival_2 + TruckService_2 >= 0
 Constraint32: - 30 x[0][3] - 30 x[1][3] - 30 x[2][3] - 30 x[4][3]
   - 30 x[5][3] - 30 x[6][3] - 30 x[7][3] - 30 x[8][3] - 30 x[9][3]
   - TruckArrival_3 + TruckService_3 >= 0
 Constraint32: - 30 x[0][4] - 30 x[1][4] - 30 x[2][4] - 30 x[3][4]
   - 30 x[5][4] - 30 x[6][4] - 30 x[7][4] - 30 x[8][4] - 30 x[9][4]
   - TruckArrival_4 + TruckService_4 >= 0
 Constraint32: - 30 x[0][5] - 30 x[1][5] - 30 x[2][5] - 30 x[3][5]
   - 30 x[4][5] - 30 x[6][5] - 30 x[7][5] - 30 x[8][5] - 30 x[9][5]
   - TruckArrival_5 + TruckService_5 >= 0
 Constraint32: - 30 x[0][6] - 30 x[1][6] - 30 x[2][6] - 30 x[3][6]
   - 30 x[4][6] - 30 x[5][6] - 30 x[7][6] - 30 x[8][6] - 30 x[9][6]
   - TruckArrival_6 + TruckService_6 >= 0
 Constraint32: - 30 x[0][7] - 30 x[1][7] - 30 x[2][7] - 30 x[3][7]
   - 30 x[4][7] - 30 x[5][7] - 30 x[6][7] - 30 x[8][7] - 30 x[9][7]
   - TruckArrival_7 + TruckService_7 >= 0
 Constraint32: - 30 x[0][8] - 30 x[1][8] - 30 x[2][8] - 30 x[3][8]
   - 30 x[4][8] - 30 x[5][8] - 30 x[6][8] - 30 x[7][8] - 30 x[9][8]
   - TruckArrival_8 + TruckService_8 >= 0
 Constraint32: - 30 x[0][9] - 30 x[1][9] - 30 x[2][9] - 30 x[3][9]
   - 30 x[4][9] - 30 x[5][9] - 30 x[6][9] - 30 x[7][9] - 30 x[8][9]
   - TruckArrival_9 + TruckService_9 >= 0
 Constraint35: - TruckService_10 + TruckCompletion_10 >= 0
 Constraint35: - TruckService_1 + TruckCompletion_1 >= 0
 Constraint35: - TruckService_2 + TruckCompletion_2 >= 0
 Constraint35: - TruckService_3 + TruckCompletion_3 >= 0
 Constraint35: - TruckService_4 + TruckCompletion_4 >= 0
 Constraint35: - TruckService_5 + TruckCompletion_5 >= 0
 Constraint35: - TruckService_6 + TruckCompletion_6 >= 0
 Constraint35: - TruckService_7 + TruckCompletion_7 >= 0
 Constraint35: - TruckService_8 + TruckCompletion_8 >= 0
 Constraint35: - TruckService_9 + TruckCompletion_9 >= 0
Bounds
 TruckService_0 = 0
 1 <= u_10 <= 11
 1 <= u_1 <= 11
 1 <= u_2 <= 11
 1 <= u_3 <= 11
 1 <= u_4 <= 11
 1 <= u_5 <= 11
 1 <= u_6 <= 11
 1 <= u_7 <= 11
 1 <= u_8 <= 11
 1 <= u_9 <= 11
Binaries
 x[0][10] x[0][1] x[0][2] x[0][3] x[0][4] x[0][5] x[0][6] x[0][7] x[0][8]
 x[0][9] x[1][10] x[1][2] x[1][3] x[1][4] x[1][5] x[1][6] x[1][7] x[1][8]
 x[1][9] x[2][10] x[2][1] x[2][3] x[2][4] x[2][5] x[2][6] x[2][7] x[2][8]
 x[2][9] x[3][10] x[3][1] x[3][2] x[3][4] x[3][5] x[3][6] x[3][7] x[3][8]
 x[3][9] x[4][10] x[4][1] x[4][2] x[4][3] x[4][5] x[4][6] x[4][7] x[4][8]
 x[4][9] x[5][10] x[5][1] x[5][2] x[5][3] x[5][4] x[5][6] x[5][7] x[5][8]
 x[5][9] x[6][10] x[6][1] x[6][2] x[6][3] x[6][4] x[6][5] x[6][7] x[6][8]
 x[6][9] x[7][10] x[7][1] x[7][2] x[7][3] x[7][4] x[7][5] x[7][6] x[7][8]
 x[7][9] x[8][10] x[8][1] x[8][2] x[8][3] x[8][4] x[8][5] x[8][6] x[8][7]
 x[8][9] x[9][10] x[9][1] x[9][2] x[9][3] x[9][4] x[9][5] x[9][6] x[9][7]
 x[9][8] p_01 p_02 p_03 p_04 p_05 p_06 p_07 p_08 p_09 p_12 p_13 p_14 p_15
 p_16 p_17 p_18 p_19 p_21 p_23 p_24 p_25 p_26 p_27 p_28 p_29 p_31 p_32 p_34
 p_35 p_36 p_37 p_38 p_39 p_41 p_42 p_43 p_45 p_46 p_47 p_48 p_49 p_51 p_52
 p_53 p_54 p_56 p_57 p_58 p_59 p_61 p_62 p_63 p_64 p_65 p_67 p_68 p_69 p_71
 p_72 p_73 p_74 p_75 p_76 p_78 p_79 p_81 p_82 p_83 p_84 p_85 p_86 p_87 p_89
 p_91 p_92 p_93 p_94 p_95 p_96 p_97 p_98
End
