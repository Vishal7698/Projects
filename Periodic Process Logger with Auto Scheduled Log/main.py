import time
import smtplib
import psutil           #Give system process utiliation information
import os
import sys
import schedule
from urllib.request import urlopen         #urllib is a Python module that can be used for opening URLs.
from email import encoders
from email.mime.text import MIMEText
from email.mime.base import MIMEBase
from email.mime.multipart import MIMEMultipart

def isConnect():
    try:
        urlopen("https://www.google.com", timeout=1)
        return True
    except:
        return False

def mailSender(fileName,time):
    try:
        fromAddres="vishal.hase@mmit.edu.in"
        toAddres="vishalhase1111@gmail.com"

        msg=MIMEMultipart()
        msg['From']=fromAddres
        msg['To']=toAddres
        body="""
        Hello %s,
        Welcome to Periodic process logger with auto scheduling"
        Following attachment contain log file of process
        Log file created at : %s
        
        This is auto genarated mail, Do not reply
        Thanks & regrads,
        Process Logger app
        """%(toAddres,time)
        subject="""
        Process log genrated at : %s
        """%(time)

        msg['Subject']=subject
        msg.attach(MIMEText(body,'plain'))
        attachment=open(fileName,"rb")
        p=MIMEBase('application','octet-stream')
        p.set_payload((attachment).read())
        encoders.encode_base64(p)
        p.add_header('Content-Disposition',"attachment; filename=%s"% fileName)
        msg.attach(p)
        s=smtplib.SMTP('smpt.gmail.com',587)
        s.starttls()
        s.login(fromAddres,'*****')
        text=msg.as_string()
        s.sendmail(fromAddres,toAddres,text)
        s.quit()
        print("log file sucessfully sent to Mail")
    except Exception as e:
        print("unable to send email ", e)



def displayProcess():
    listProcess=[]
    for proc in psutil.process_iter():
        try:
            pinfo=proc.as_dict(attrs=['pid','name','username','status'])
            pinfo['vms']=proc.memory_info().vms/(1024*1024)             #vms is virtual memory size
            listProcess.append(pinfo)
        except(psutil.NoSuchProcess,psutil.AccessDenied,psutil.ZombieProcess):
            pass
    return listProcess

def processLog(log_dir='sysLog'):
    listProcess = []
    if not os.path.exists(log_dir):
        try:
            os.mkdir(log_dir)
        except:
            pass
    seprator='_'*80
    log_path=os.path.join(log_dir,'syslog.log'%(time.ctime()))
    f=open(log_path,'w')
    f.write(seprator+'\n')
    f.write('process logs'+time.ctime()+'\n')
    f.write(seprator + '\n')
    f.write('\n')

    for proc in psutil.process_iter():
        try:
            pinfo = proc.as_dict(attrs=['pid', 'name', 'username', 'status'])
            pinfo['vms'] = proc.memory_info().vms / (1024 * 1024)  # vms is virtual memory size
            listProcess.append(pinfo)
        except(psutil.NoSuchProcess, psutil.AccessDenied, psutil.ZombieProcess):
            pass
    for element in listProcess:
        f.write("%s\n"%element)

    print("log file is succesfully genrated at location %s",log_path)

    conected=isConnect()
    if conected:
        startTime=time.time()
        mailSender(log_path,time.ctime())
        endTime=time.time()
        print("Took %s second to send email"%(endTime-startTime))
    else:
        print("No internet Connection")


def main():
    print("Application name :"+sys.argv[0])
#    print("enter the time for interval (give inteval in minute)")

    schedule.every(1).minute.do(displayProcess())
    while True:
        schedule.run_pending()
        time.sleep(1)


if __name__=="__main__":
    main()