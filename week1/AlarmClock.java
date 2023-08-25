import java.util.Scanner;

public class AlarmClock
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int alarmHour = input.nextInt();
        int alarmMinute = input.nextInt();
        int alarmInMinutes = alarmHour * 60 + alarmMinute;

        int currentHour = 0;
        int currentMinute = 0;
        int currentTimeInMinutes = currentHour * 60 + currentMinute;

        int falseAlarm = -1;

        do
        {
            falseAlarm = falseAlarm + 1;
            currentHour = input.nextInt();
            currentMinute = input.nextInt();
            currentTimeInMinutes = currentHour * 60 + currentMinute;
        }
        while(currentTimeInMinutes < alarmInMinutes);
        {
            System.out.println("false alarms: " + falseAlarm);
        }
    }
}
