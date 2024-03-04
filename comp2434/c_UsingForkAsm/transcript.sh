#!/bin/bash

# got aid from chatgpt

# Extract the subject files and student IDs to two separate list
subjectData=()
# store the input studentIds
studentId=()
# store the extracted studentIds + name
studentInfo=()
# marker for "student"
marker=false
studentDat="student.dat"

for arg in "$@"; do
  if [[ $arg == "student" ]]; then
    marker=true
  # when the marker is false, it is the first part of argument list (dat files for subjects)
  elif [[ $marker == false ]]; then 
    subjectData+=("$arg")
  # when the marker is true, it is the second part of argument list (students to be extracted)
  elif [[ $marker == true ]]; then
    # block the input of wildcard for studentIDs
    if [[ "$arg" == *"*"* ]]; then
      echo "Wildcard not allowed for studentIDs."
    else
    #bonus part: if the input argument is not integer(IDs) but name, get the matched IDs
      if [[ $arg =~ ^[0-9]+$ ]]; then
        studentId+=("$arg")
        continue
      else
        # read student.dat to get all matched student info
        corrospondInfo=$(grep -i "$arg" "$studentDat")
        #split to elements in an array
        while IFS= read -r line; do
          splited+=("$line")
        done <<< "$corrospondInfo"
        # append the results to the list
        for studentInfoLine in "${splited[@]}"; do
          read -r getId _ <<< "$studentInfoLine"
          studentId+=("$getId")
        done
      fi
    fi
  fi
done

# Function to generate the transcript for a given student ID
generate_transcript() {
  # store the input student IDs + names
  local student_Info=$1
  # store the result to be print
  local studentResult=()
  # store the processed grade-only, to be calculated for the gpa
  local studentGrades=()
  echo "Transcript for $student_Info"
  # Loop through each subject file
  for file in "${subjectData[@]}"; do
    # Find the subject details
    subjectInfo=$(grep "Subject" "$file")
    # assign the details to variables, for further printout
    read -r _ subjectCode year semester <<< "$subjectInfo"
    # Find the student's grade in the subject, read file get the ID and grade of each line
    while read -r student grade || [[ -n $student ]]; do
      # if the student handling is matched with the line
      if [[ $student == "${student_Info%% *}" ]]; then
        # append new line to be print
        studentResult+=("$subjectCode $year Sem $semester $grade")
        # append the grade first, if no grade automatically skip
        studentGrades+=("$grade")
        # check the duplicated grade and save the latest only, for calculation
        for ((i=0; i<${#studentResult[@]}; i++)); do
          read -r subj yr _ sem grade <<< "${studentResult[i]}"
          if [[ $subj == "$subjectCode" ]]; then
            # not the different year
            if [[ $year > "$yr" ]]; then
              # set 0, it is a invalid input for gpa calculation, will be ignored afterwards
              studentGrades[i]="0"
            # same year, different semester
            elif [[ $year == "$yr" && $semester > "$sem" ]]; then
              studentGrades[i]="0"
            fi
          fi
        done
        break
      fi
    done < "$file"
    
  done

  # print all the subjects have been taken
  for result in "${studentResult[@]}"; do
      echo "$result"
  done

 # Calculate GPA, referenc/convert from lab1
  # store the number of subjects to be divided (involved in the calculation)
  local num_subj=${#studentGrades[@]}
  # store the sum of score for all subject
  local sum_gp=0
  # store the divided score
  local gpa=0

  #go through all the grades
  for in_grade in "${studentGrades[@]}"; do
    # flag for the validation of the grades, if not valid, subtract num_subj by 1 and add nothing to sum_gp
    # so that it does not involved in the calculation
    grade_valid=1

    # check the grade and allocate the corrosponding score
    # get the first character
    case ${in_grade:0:1} in
    # avoid handling decimals
      A) in_gp=400;;
      B) in_gp=300;;
      C) in_gp=200;;
      D) in_gp=100;;
      F) in_gp=0;;
      *) grade_valid=0;;
    esac
    
    if (( grade_valid == 0 )); then
      ((num_subj--))
      continue
    fi

    # check the second character, denote the sub-grade
    if [[ ${in_grade:1:1} == "+" ]]; then
      in_gp=$((in_gp + 30))
    fi

    if [[ ${in_grade:1:1} == "-" ]]; then
      in_gp=$((in_gp - 30))
    fi

    sum_gp=$((sum_gp + in_gp))
  done

  # avoid zero division
  if [[ $num_subj -gt 0 ]]; then
    gpa=$((sum_gp / num_subj))
  else
    # assign string of "000" to be output as "0.00" simply
    gpa="000"
  fi

  echo "GPA for $num_subj subjects ${gpa:0:1}.${gpa:1:2}"

  
}

# Loop through each student ID and generate the transcript
for id in "${studentId[@]}"; do
  # extract the student ID + name
  studentInfo=$(grep "$id" "$studentDat")
  # step in the function
  generate_transcript "$studentInfo"
  # print a new line
  echo ""
  echo
done