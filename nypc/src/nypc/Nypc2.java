package nypc;

import java.util.*;import java.awt.event.ItemEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Nypc2 {
	
	public static void main(String[] args) {
		class item{
			SimpleDateFormat f = new SimpleDateFormat("m:ss.SS", Locale.KOREA);
			int team;
			long time;
			String stime;
			public item() {}
			public item(int v,String c) {
				this.team = v;
				this.stime = c;
				try {
					this.time = f.parse(c).getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		class Asc implements Comparator<item> {
			@Override
			public int compare(item arg0, item arg1) {
				return (arg0.time < arg1.time) ? -1: (arg0.time > arg1.time) ? 1:0 ;
			}
		 
		}
		int[] score = {10,8,6,5,4,3,2,1};
		Scanner scan = new Scanner(System.in);
		int T;
		T = scan.nextInt();
		scan.nextLine();
		while(T>0) {
			String str;
			str = scan.nextLine();
			List<item> list = new ArrayList<item>();
			for(int i=0;i<8;i++) {
				String[] cur = scan.nextLine().split(" ");
				int tt = 0;
				if(cur[0].equals("blue")) {
					tt = 1;
				}else {
					tt = 2;
				}
				list.add(new item(tt,cur[1]));
			}
			Asc asc = new Asc();
			Collections.sort(list, asc);
			
			/*for(int i=0;i<list.size();i++) {
				System.out.println(list.get(i).team+" "+list.get(i).stime+" "+(list.get(i).time-list.get(0).time));
			}*/
			if(str.equals("item")) {
				System.out.println(list.get(0).team == 1 ? "blue" : "red");
			}else {
				int cnt = 0, sblue = 0, sred = 0;
				for(int i=0;i<list.size();i++) {
					if((list.get(i).time-list.get(0).time)>=10000) break;
					if(list.get(i).team == 1) sblue += score[cnt];
					else sred += score[cnt];
					cnt++;
				}
				if(sred == sblue) {
					System.out.println(list.get(0).team == 1 ? "blue" : "red");
				}else {
					if(sred > sblue) System.out.println("red");
					else System.out.println("blue");
				}
			}
			T--;
		}
	}
}
