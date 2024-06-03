package com.bishnu.codewithme.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TaskScheduler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input from the user
        System.out.print("Enter the number of tasks: ");
        int numTasks = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter the start date (MM/dd/yyyy): ");
        String startDateStr = scanner.nextLine();

        System.out.print("Enter the end date (MM/dd/yyyy): ");
        String endDateStr = scanner.nextLine();

        System.out.print("Enter the start time (hh:mm AM/PM, e.g., 09:00 AM): ");
        String startTimeStr = scanner.nextLine();

        System.out.print("Enter the end time (hh:mm AM/PM, e.g., 05:00 PM): ");
        String endTimeStr = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        try {
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);
            Date startTime = timeFormat.parse(startTimeStr);
            Date endTime = timeFormat.parse(endTimeStr);

            long totalTimeInMillis = endDate.getTime() - startDate.getTime() + 24 * 60 * 60 * 1000; // Add one day for inclusive end date
            long taskDurationInMillis = totalTimeInMillis / numTasks;

            List<Task> taskSchedule = new ArrayList<>();

            for (int i = 0; i < numTasks; i++) {
                Date taskStartTime = new Date(startDate.getTime() + i * taskDurationInMillis + startTime.getTime());
                Date taskEndTime = new Date(taskStartTime.getTime() + taskDurationInMillis);

                taskSchedule.add(new Task("Task " + (i + 1), dateFormat.format(startDate), dateFormat.format(endDate),
                        timeFormat.format(taskStartTime), timeFormat.format(taskEndTime)));
            }

            System.out.println("\nTask Schedule:");
            for (Task task : taskSchedule) {
                System.out.println(task);
            }
        } catch (ParseException e) {
            System.out.println("Invalid date or time format. Please follow the specified formats.");
        }
    }
}
