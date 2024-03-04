// lab 1C: please fix the program
#include <stdio.h>
int main(int argc, char *argv[])
{
	 int num_subj;
	 float in_gp, sum_gp = 0.0;
	 char in_grade;
	 int i;
	 // argv[0] is the name of the program 
	 num_subj = argc-1;
	 int count = num_subj;
	 printf("PolyU System:\n");
	 for (i = 1; i <= count; i++) {
		 in_grade = argv[i][0]; // get the first character
		 int grade_valid = 1;
		 switch (in_grade) {
			 case 'A': in_gp = 4.0; break;
			 case 'B': in_gp = 3.0; break;
			 case 'C': in_gp = 2.0; break;
			 case 'D': in_gp = 1.0; break;
			 case 'F': in_gp = 0.0; break;
			 default: grade_valid = 0;
		 }

		 if ((in_grade == 'D') && (argv[i][1] == '-')) grade_valid = 0;
		 if (grade_valid == 0){
			 printf("Grade for subject %d is %s, invalid\n",i,argv[i]);
			 num_subj--;
			 continue;
		 }
		 if (argv[i][1] == '+') in_gp = in_gp + 0.3;
		 if (argv[i][1] == '-') in_gp = in_gp - 0.3;
		 printf("Grade for subject %d is %s, GP%4.1f\n",i,argv[i],in_gp);
		 sum_gp = sum_gp + in_gp;
	 }
	 printf("Your GPA for %d valid subjects is%5.2f\n",num_subj,sum_gp/num_subj);
	 printf("Other System:\n");
	 sum_gp = 0.0;
	 num_subj = count;
	 for (i = 1; i <= count; i++) {
		 in_grade = argv[i][0]; // get the first character
		 int grade_valid = 1;
		 switch (in_grade) {
			 case 'A': in_gp = 11.0; break;
			 case 'B': in_gp = 8.0; break;
			 case 'C': in_gp = 5.0; break;
			 case 'D': in_gp = 2.0; break;
			 case 'F': in_gp = 0.0; break;
			 default: grade_valid = 0;
			 
		 }
		 if (grade_valid == 0){
			 printf("Grade for subject %d is %s, invalid\n",i,argv[i]);
			 num_subj--;
			 continue;
		 }
		 if (argv[i][1] == '+') in_gp = in_gp + 1.0;
		 if (argv[i][1] == '-') in_gp = in_gp - 1.0;
		 printf("Grade for subject %d is %s, GP %.0f\n",i,argv[i],in_gp);
		 sum_gp = sum_gp + in_gp;
	 }
	 printf("Your GPA for %d valid subjects is%5.2f\n",num_subj,sum_gp/num_subj);
}