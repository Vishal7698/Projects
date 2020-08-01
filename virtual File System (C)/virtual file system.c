#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<unistd.h>
//#include<iostream>  			//for windows
//#include<io.h>				// for windows

#define MAXINODE 50			//max no of files

#define READ 1
#define WRITE 2
//for read and write use 3
#define MAXFILESIZE 2048			//max size of file

#define REGULAR 1				//file types
#define SPECIAL 2					//file type

#define START 0					//0 1 2 offset of files
#define CURRENT 1
#define END 2

typedef struct superblock			
{
   int TotalInodes;
   int FreeInode; 
}SUPERBLOCK, *PSUPERBLOCK;

typedef struct inode			
{
  char FileName[MAXINODE];			//file name	
  int InodeNumber;
  int FileSize;				//capacity
  int FileActualSize;			//data size
  int FileType;				//1 or 2 i.e regular or special
  char *Buffer;				//character pointer which store the address in which memory get allocated for store the data//backbone//all data operation through thos block//act as bufer
  int LinkCount;			//1
  int ReferenceCount;		//how many user use it
  int permission;			//1-read//2-write//3-read and write//file create permission
  struct inode *next; 			//self refrencial structure//maintain the link list//
}INODE, *PINODE, **PPINODE;

typedef struct filetable		
{
  int readoffset;
  int writeoffset, count;					//count is always 1
  int mode;						//file open mode//
  PINODE ptrinode; 					//
}FILETABLE, *PFILETABLE;

typedef struct ufdt
{
  PFILETABLE ptrfiletable;
}UFDT;

UFDT UFDTArr[MAXINODE];		//array of structure//globle
SUPERBLOCK SUPERBLOCKobj;	//Globle
PINODE head = NULL;				//globle//inode linklist head created//currently set to null

void man(char *name)
{
  if(name == NULL)
    return;
  
  if(strcmp(name, "create") == 0)
  {
     printf("Description : Used to create new regular file\n");
     printf("Usage : create File_name Permission\n");
  }
  else if(strcmp(name, "read") == 0)
  {
     printf("Description : Used to read data from regular file\n");
     printf("Usage : read File_name & No_Of_Bytes_To_Read\n");
  }
  else if(strcmp(name,"write") == 0)
  {
     printf("Description : Used to write into regular file\n");
     printf("Usage : write File_name\nAfter this enter the data that we want to  write\n");
  }
  else if(strcmp(name,"ls") == 0)
  {		
     printf("Description : Used to list all information of files\n");
     printf("Usage : ls\n");
  }
  else if(strcmp(name,"stat") == 0)
  {
     printf("Description : Used to dispaly information of file\n");
     printf("Usage : stat File_name\n");
  }
  else if(strcmp(name,"fstat") == 0)
  {
     printf("Description : Used to display information of file\n");
     printf("Usage : fstat File_Descriptor\n");
  }
  else if(strcmp(name, "truncate") == 0)
  {
     printf("Description : Used to remove data from file\n");
     printf("Usage : truncate File_name\n"); 
  }
  else if(strcmp(name, "open") == 0)
  {
     printf("Description : Used to open existing file\n");
     printf("Usage : open File_name mode\n");
  }
  else if(strcmp(name, "close") == 0)
  {
     printf("Description : Used to close opened file\n");
     printf("Usage : close File_name\n");
  }
  else if(strcmp(name, "closeall") == 0)
  {
     printf("Description : Used to close all opened file\n");
     printf("Usage : closeall\n");
  }
  else if(strcmp(name, "lseek") == 0)
  {
     printf("Description : Used to change file offset\n");
     printf("Usage : lseek File_name ChangeInOffest StartPoint\n");
  }
  else if(strcmp(name, "rm") == 0)
  {
     printf("Description : Used to delete file\n");
     printf("Usage : rm File_name\n");
  }
  else
  {
    printf("ERROR : No manual entry available\n");
  }
}

void DisplayHelp()				//to disply command and its meaning
{
    printf("ls : To List out all files\n");
    printf("clear : To clear console\n");
    printf("open : To open the file\n");
    printf("close : To close the file\n");
    printf("closeall : To close all opened files\n");
    printf("read : To Read the contents from file\n");
    printf("write :To Write contents into file\n");
    printf("exit : To Terminate file system\n");
    printf("stat : To Display information of file using name\n");
    printf("fstat :To Display information of file using file descriptor\n");
    printf("truncate : To Remove all data from file\n");
    printf("rm : To Delet the file\n");
}

int GetFDFromName(char *name)
{
    int i=0;

    while(i<MAXINODE)
    {
        if(UFDTArr[i].ptrfiletable != NULL)
           if(strcmp((UFDTArr[i].ptrfiletable->ptrinode->FileName), name) == 0)
               break;
        i++;
    }

    if(i == MAXINODE)
      return -1;
    else
      return i;				//return fd//index of array of UFDT//start from 0
} 

PINODE Get_Inode(char *name)
{
    PINODE temp = head;
    int i = 0;
  
    if(name == NULL)
       return NULL;

    while(temp != NULL)
    {
        if(strcmp(name, temp->FileName) == 0)
           break;
       
        temp = temp->next;
    }
    return temp;
}

void CreateDILB()
{
    int i=1;
    PINODE newn = NULL;
    PINODE temp = head;

    while(i<=MAXINODE)
    {
       newn = (PINODE) malloc(sizeof(INODE));		//86

       newn->LinkCount = 0;
       newn->ReferenceCount = 0;
       newn->FileType = 0;
       newn->FileSize = 0;
  
       newn->Buffer = NULL;
       newn->next = NULL;

       newn->InodeNumber = i;
       if(temp == NULL)
       {
          head = newn;
          temp = head;
       }
       else
       {
          temp->next = newn;
          temp = temp->next;
       }
        i++;
    }

    printf("DILB created successfully\n");
}

void InitialiseSuperBlock()
{
    int i=0;

    while(i<MAXINODE)
    {
       UFDTArr[i].ptrfiletable = NULL;			//for safer code set all array to null
       i++;
    }

    SUPERBLOCKobj.TotalInodes = MAXINODE;		//at the start total node ==max//its globle i.e.it accessible anywere
    SUPERBLOCKobj.FreeInode = MAXINODE;		//at the start all are free i.e. free node ==max//its globle i.e.it accessible anywere
}

int CreateFile(char *name, int permission)			//
{
    int i=0;
    PINODE temp = head;

    if((name == NULL) || (permission == 0) || (permission>3))
        return -1;

    if(SUPERBLOCKobj.FreeInode == 0)
        return -2;

    (SUPERBLOCKobj.FreeInode)--;

    if(Get_Inode(name) != NULL)
        return -3;

    while(temp!=NULL)
    {
        if(temp->FileType == 0)
           break;
        temp = temp->next;
    }

    while(i<MAXINODE)
    {
       if(UFDTArr[i].ptrfiletable == NULL)
           break;
       i++;
    }

    UFDTArr[i].ptrfiletable = (PFILETABLE) malloc(sizeof(FILETABLE));

    UFDTArr[i].ptrfiletable->count = 1;
    UFDTArr[i].ptrfiletable->mode = permission;
    UFDTArr[i].ptrfiletable->readoffset = 0;
    UFDTArr[i].ptrfiletable->writeoffset = 0;

    UFDTArr[i].ptrfiletable->ptrinode = temp;

    strcpy(UFDTArr[i].ptrfiletable->ptrinode->FileName,name);
    UFDTArr[i].ptrfiletable->ptrinode->FileType = REGULAR;
    UFDTArr[i].ptrfiletable->ptrinode->ReferenceCount = 1;
    UFDTArr[i].ptrfiletable->ptrinode->LinkCount = 1;
    UFDTArr[i].ptrfiletable->ptrinode->FileSize = MAXFILESIZE;
    UFDTArr[i].ptrfiletable->ptrinode->FileActualSize = 0;
    UFDTArr[i].ptrfiletable->ptrinode->permission = permission;
    UFDTArr[i].ptrfiletable->ptrinode->Buffer = (char*)malloc(MAXFILESIZE);

    return i;
}

int rm_File(char* name)
{
    int fd=0;

    fd = GetFDFromName(name);				
    if(fd == -1)
       return -1;

    (UFDTArr[fd].ptrfiletable->ptrinode->LinkCount)--;				//linkcount is set to 0 which is always 1

    if(UFDTArr[fd].ptrfiletable->ptrinode->LinkCount == 0)
    {
	UFDTArr[fd].ptrfiletable->ptrinode->ReferenceCount = 0;
	UFDTArr[fd].ptrfiletable->ptrinode->permission = 0;
	UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize = 0;
        UFDTArr[fd].ptrfiletable->ptrinode->FileType = 0;			//file type set to 0
	free(UFDTArr[fd].ptrfiletable->ptrinode->Buffer);			//free the buffer
	strcpy(UFDTArr[fd].ptrfiletable->ptrinode->FileName,"");
        free(UFDTArr[fd].ptrfiletable);
    }
    
    UFDTArr[fd].ptrfiletable = NULL;
    (SUPERBLOCKobj.FreeInode)++;
}

int ReadFile(int fd, char* arr, int isize)
{
    int read_size = 0;

    if(UFDTArr[fd].ptrfiletable == NULL)
        return -1;

    if(UFDTArr[fd].ptrfiletable->mode !=READ && UFDTArr[fd].ptrfiletable->mode !=READ+WRITE)
        return -2;

    if(UFDTArr[fd].ptrfiletable->ptrinode->permission != READ && UFDTArr[fd].ptrfiletable->ptrinode->permission != READ+WRITE) 
        return -2;

    if(UFDTArr[fd].ptrfiletable->readoffset == UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize)
        return -3;

    if(UFDTArr[fd].ptrfiletable->ptrinode->FileType != REGULAR)      
        return -4;

    read_size = (UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize) - (UFDTArr[fd].ptrfiletable->readoffset);
    if(read_size < isize)
    {
 
        strncpy(arr,(UFDTArr[fd].ptrfiletable->ptrinode->Buffer) + (UFDTArr[fd].ptrfiletable->readoffset),read_size);

        UFDTArr[fd].ptrfiletable->readoffset = UFDTArr[fd].ptrfiletable->readoffset + read_size;
    }
    else
    {
        strncpy(arr,(UFDTArr[fd].ptrfiletable->ptrinode->Buffer) + (UFDTArr[fd].ptrfiletable->readoffset),isize);

        (UFDTArr[fd].ptrfiletable->readoffset) = (UFDTArr[fd].ptrfiletable->readoffset) + isize;
    }

    return isize;
}

int WriteFile(int fd, char *arr, int isize)			//writefile(index_of_ufdt,arr_baseAddress,Array_size)
{ 
    if(((UFDTArr[fd].ptrfiletable->mode) !=WRITE) && ((UFDTArr[fd].ptrfiletable->mode) != READ+WRITE)) 		//if(mode !=2 && mode !=3)
        return -1;

    if(((UFDTArr[fd].ptrfiletable->ptrinode->permission) !=WRITE) && ((UFDTArr[fd].ptrfiletable->ptrinode->permission) != READ+WRITE))	//if(mode!=2&&3)
        return -1;

    if((UFDTArr[fd].ptrfiletable->writeoffset) == MAXFILESIZE)		//writeoffset!=maxfilesize//theire is no space for write//modify the code if space is remain and it is grater than size of arr which can be greater than space remain
        return -2;

    if((UFDTArr[fd].ptrfiletable->ptrinode->FileType) != REGULAR) 	//we set filetype as always 1//it is always regular
        return -3;

    if(((UFDTArr[fd].ptrfiletable->ptrinode->FileSize)-(UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize))<isize)
	return -4;
	
	
    strncpy((UFDTArr[fd].ptrfiletable->ptrinode->Buffer) + (UFDTArr[fd].ptrfiletable->writeoffset),arr,isize);

    (UFDTArr[fd].ptrfiletable->writeoffset) = (UFDTArr[fd].ptrfiletable->writeoffset )+ isize;

    (UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize) = (UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize) + isize;

    return isize;
}

int OpenFile(char *name, int mode)
{
    int i = 0;
    PINODE temp = NULL;
  
    if(name == NULL || mode <= 0)
        return -1;

    temp = Get_Inode(name);
    if(temp == NULL)  
        return -2;

    if(temp->permission < mode)
        return -3;

    while(i<MAXINODE)
    {
        if(UFDTArr[i].ptrfiletable == NULL)
             break;
        i++;
    }

    UFDTArr[i].ptrfiletable = (PFILETABLE)malloc(sizeof(FILETABLE));
    if(UFDTArr[i].ptrfiletable == NULL)
         return -1;
    UFDTArr[i].ptrfiletable->count = 1;
    UFDTArr[i].ptrfiletable->mode = mode;
    if(mode == READ + WRITE)
    {  
        UFDTArr[i].ptrfiletable->readoffset = 0;
        UFDTArr[i].ptrfiletable->writeoffset = 0;
    }
    else if(mode == READ)
    {
       UFDTArr[i].ptrfiletable->readoffset = 0;
    }
    else if(mode == WRITE)
    {
       UFDTArr[i].ptrfiletable->writeoffset = 0;
    }
  
    UFDTArr[i].ptrfiletable->ptrinode = temp;
    (UFDTArr[i].ptrfiletable->ptrinode->ReferenceCount)++;

    return i;
}

void CloseFileByName1(int fd)
{
    UFDTArr[fd].ptrfiletable->readoffset = 0;
    UFDTArr[fd].ptrfiletable->writeoffset = 0;
    (UFDTArr[fd].ptrfiletable->ptrinode->ReferenceCount)--;
}

int CloseFileByName(char *name)
{
    int i = 0;
    i = GetFDFromName(name);			//get fd by using name for close file for close

    if(i == -1)
        return -1;

    UFDTArr[i].ptrfiletable->readoffset = 0;
    UFDTArr[i].ptrfiletable->writeoffset = 0;
    (UFDTArr[i].ptrfiletable->ptrinode->ReferenceCount)--;		//reffrence count is use to detect where the file is open or not

    return 0;
}

void CloseAllFile()			
{
   int i = 0;
  
   while(i<MAXINODE)
   {
       if(UFDTArr[i].ptrfiletable != NULL)
       {
           UFDTArr[i].ptrfiletable->readoffset = 0;
           UFDTArr[i].ptrfiletable->writeoffset = 0;
           //(UFDTArr[i].ptrfiletable->ptrinode->ReferenceCount)--;
	   UFDTArr[i].ptrfiletable->ptrinode->ReferenceCount=0;	
        }
        i++;
    }
}

int LseekFile(int fd, int size, int from)
{
    if((fd<0) || (from > 2))
        return -1;
    
    if(UFDTArr[fd].ptrfiletable == NULL)
        return -1;

    if((UFDTArr[fd].ptrfiletable->mode == READ) || (UFDTArr[fd].ptrfiletable->mode == READ+WRITE))
    {
        if(from == CURRENT)
        {
            if(((UFDTArr[fd].ptrfiletable->readoffset) + size) > UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize)
                 return -1;
            
            if(((UFDTArr[fd].ptrfiletable->readoffset) + size) < 0) 
                 return -1;
            
            (UFDTArr[fd].ptrfiletable->readoffset) = (UFDTArr[fd].ptrfiletable->readoffset) + size;
         }
         else if(from == START)
         { 
             if(size>(UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize))          return -1;
 
              if(size < 0) return -1;

              (UFDTArr[fd].ptrfiletable->readoffset) = size;
         }
         else if(from == END)
         {
              if((UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize) + size > MAXFILESIZE)
                  return -1;

              if(((UFDTArr[fd].ptrfiletable->readoffset) + size) < 0) 
                  return -1;

              (UFDTArr[fd].ptrfiletable->readoffset) = (UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize) + size;
          }
    }
    else if(UFDTArr[fd].ptrfiletable->mode == WRITE)
    {
         if(from == CURRENT)
         {
             if(((UFDTArr[fd].ptrfiletable->writeoffset) + size) > MAXFILESIZE)
                return -1;

             if(((UFDTArr[fd].ptrfiletable->writeoffset) + size) < 0)
                return -1;

             if(((UFDTArr[fd].ptrfiletable->writeoffset) + size) > (UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize))
                  (UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize) =(UFDTArr[fd].ptrfiletable->writeoffset) + size;

             (UFDTArr[fd].ptrfiletable->writeoffset) = (UFDTArr[fd].ptrfiletable->writeoffset) + size;
         }
         else if(from == START)
         {
             if(size > MAXFILESIZE) return -1;
             if(size < 0) return -1;
             if(size > (UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize))
                 (UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize) = size;
   
             (UFDTArr[fd].ptrfiletable->writeoffset) = size;
         }
         else if(from == END)
         {
             if((UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize) + size >MAXFILESIZE)
                 return -1;
             
             if(((UFDTArr[fd].ptrfiletable->writeoffset) + size) < 0) 
                 return -1;
 
             (UFDTArr[fd].ptrfiletable->writeoffset) = (UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize) + size;
         }
    }
}

void ls_file()							//it display the list all the files
{
    int i = 0;
    PINODE temp = head;

    if(SUPERBLOCKobj.FreeInode == MAXINODE)
    {
        printf("Error : There are no files\n");
        return;
    }

    printf("\nFile Name\tInode number\tFile size\tLink count\n");
    printf("---------------------------------------------------------------\n");
    while(temp != NULL)
    {
        if(temp->FileType != 0)
        {
           printf("%s\t\t%d\t\t%d\t\t%d\n",temp->FileName,
           temp->InodeNumber,temp->FileActualSize,temp->LinkCount);		//custum the printf to change the datail of information if you want
        }
        temp = temp->next;
    }
    printf("---------------------------------------------------------------\n");
}

int fstat_file(int fd)
{
    PINODE temp = head;
    int i=0;

    if(fd<0)
       return -1;

    if(UFDTArr[fd].ptrfiletable == NULL)
       return -2;

    temp = UFDTArr[fd].ptrfiletable->ptrinode;

    printf("\n---------Statistical Information about file----------\n");
    printf("File name : %s\n",temp->FileName);
    printf("Inode Number %d\n",temp->InodeNumber);
    printf("File size : %d\n",temp->FileSize);
    printf("Actual File size : %d\n",temp->FileActualSize);
    printf("Link count : %d\n",temp->LinkCount);
    printf("Reference count : %d\n",temp->ReferenceCount);

    if(temp->permission == 1)
        printf("File Permission : Read only\n");
    else if(temp->permission == 2)
        printf("File Permission : Write\n");
    else if(temp->permission == 3)
        printf("File Permission : Read & Write\n");
    printf("------------------------------------------------------\n\n");

    return 0;
}

int stat_file(char *name)
{
    PINODE temp = head;
    int i=0;

    if(name == NULL)
        return -1;
    
    while(temp!=NULL)
    {
        if(strcmp(name,temp->FileName) == 0)
            break;
        temp = temp->next;
    }
  
    if(temp == NULL)
       return -2;

    printf("\n---------Statistical Information about file----------\n");
    printf("File name : %s\n",temp->FileName);
    printf("Inode Number %d\n",temp->InodeNumber);
    printf("File size : %d\n",temp->FileSize);
    printf("Actual File size : %d\n",temp->FileActualSize);
    printf("Link count : %d\n",temp->LinkCount);
    printf("Reference count : %d\n",temp->ReferenceCount);

    if(temp->permission == 1)
         printf("File Permission : Read only\n");
    else if(temp->permission == 2)
         printf("File Permission : Write\n");
    else if(temp->permission == 3)
         printf("File Permission : Read & Write\n");
    
    printf("------------------------------------------------------\n\n");

    return 0;
}

int truncate_File(char *name)
{
    int fd = GetFDFromName(name);
    if(fd == -1)
        return -1;

    memset(UFDTArr[fd].ptrfiletable->ptrinode->Buffer,0,1024);
    UFDTArr[fd].ptrfiletable->readoffset = 0;
    UFDTArr[fd].ptrfiletable->writeoffset = 0;
    UFDTArr[fd].ptrfiletable->ptrinode->FileActualSize = 0;
}

int main()
{
    char *ptr = NULL;
    int ret=0, fd=0, count=0;
    char command[4][80], str[80], arr[MAXFILESIZE];

    InitialiseSuperBlock();		
    CreateDILB();	

    while(1)
    {
        fflush(stdin);
        strcpy(str,"");

        printf("\nMarvellous VFS : > ");

        fgets(str,80,stdin);

        count = sscanf(str,"%s %s %s %s",command[0],command[1],command[2],command[3]);

        if(count == 1)
        {
            if(strcmp(command[0],"ls") == 0)
            {
                ls_file();
            }
            else if(strcmp(command[0],"closeall") == 0)
            {
                CloseAllFile();
                printf("All files closed successfully\n");
                continue;
            }
            else if(strcmp(command[0],"clear") == 0)
            {
                system("clear");
                continue;
            }
            else if(strcmp(command[0],"help") == 0)
            {
                DisplayHelp();
                continue;
            }
            else if(strcmp(command[0],"exit") == 0)
            {
                printf("Terminating the Marvellous Virtual File System\n");
                break;
            }
            else
            {
                printf("\nERROR : Command not found !!\n");
                continue;
            }
      }
      else if(count == 2)
      {
            if(strcmp(command[0],"stat") == 0)				//stat Demo.exe //(format of command)
            {
               ret = stat_file(command[1]);
               if(ret == -1)
                   printf("ERROR : Incorrect parameters\n");
               if(ret == -2)
                   printf("ERROR : There is no such file\n");
               continue;
            }
            else if(strcmp(command[0],"fstat") == 0)			//fstat 0	//0 is file discriptor//change o as its file descriptor
            {
               ret = fstat_file(atoi(command[1]));
               if(ret == -1)
                   printf("ERROR : Incorrect parameters\n");

               if(ret == -2)
                   printf("ERROR : There is no such file\n");
               continue;
            }
            else if(strcmp(command[0],"close") == 0)			//close demo.txt//cmd for close//internally file name use to get fd and with the help of fd ,fd is use to close the file//for user pupose we use filename.
            {
               ret = CloseFileByName(command[1]);			
          
               if(ret == -1)
                   printf("ERROR : There is no such file\n");
               continue;
            }
            else if(strcmp(command[0],"rm") == 0)		//to remove file//unlink() function it is the system call
            {
                ret = rm_File(command[1]);			//rm demo.txt //command is use as
                if(ret == -1)
                     printf("ERROR : There is no such file\n");
                continue;
            }
            else if(strcmp(command[0],"man") == 0)		//command and its imformation
            {
                man(command[1]);						// command[1] is parameter 
            }
            else if(strcmp(command[0],"write") == 0)		//write Demo.txt
            {
                fd = GetFDFromName(command[1]);
                if(fd == -1)
                {
                    printf("Error : Incorrect parameter\n");
                    continue;
                }
                printf("Enter the data : \n");				
		fflush(stdin);					//flush the scanf
                scanf("%[^\n]",arr);				//take data to write
		
                ret = strlen(arr);				//ret contain length of data by using strlen
                if(ret == 0)
                {
                    printf("Error : Incorrect parameter\n");
                    continue;
                }
         
                ret = WriteFile(fd,arr,ret);			//writefile(index_of_ufdt(fd),arr_baseAddress,Array_size)
                if(ret == -1)
                    printf("ERROR : Permission Denied !!\n");

                if(ret == -2)
                    printf("ERROR : There is no sufficient memeory to write!\n");

                 if(ret == -3)
                    printf("ERROR : It is not regular file !!\n");

		if(ret >0)
			printf("write in sucess");
		
		if(ret == -4)
			printf("there is no sufficient memory");
            }
            else if(strcmp(command[0],"truncate") == 0)
            {
                ret = truncate_File(command[1]);
                if(ret == -1)
                     printf("Error : Incorrect parameter\n");
            }
            else
            {
                printf("\nERROR : Command not found !!!\n");
                continue;
            }
       }
       else if(count == 3)
       {
            if(strcmp(command[0],"create") == 0)			//create flename 1/2/
            {
                ret = CreateFile(command[1],atoi(command[2]));
                if(ret >= 0)
                    printf("File is successfully created with fd : %d\n",ret);

                if(ret == -1)
                    printf("ERROR : Incorrect parameters\n");

                if(ret == -2)
                    printf("ERROR : There is no inodes\n");

                if(ret == -3)
                    printf("ERROR : File already exists\n");
 
                if(ret == -4)
                    printf("ERROR : Memory allocation failure\n");
                continue;
            }
            else if(strcmp(command[0],"open") == 0)			//cat is use to open file in linux//cat is system call
            {
                ret = OpenFile(command[1],atoi(command[2]));
                if(ret >= 0)
                    printf("File is successfully opened with fd : %d\n",ret);

                if(ret == -1)
                    printf("ERROR : Incorrect parameters\n");

                if(ret == -2)
                    printf("ERROR : File not present\n");

                if(ret == -3)
                    printf("ERROR : Permission denied\n");
                continue;
           }
           else if(strcmp(command[0],"read") == 0)
           {
                fd = GetFDFromName(command[1]);
                if(fd == -1)
                {
                    printf("Error : Incorrect parameter\n");
                    continue;
                }
        
                ptr = (char *)malloc(sizeof(atoi(command[2]))+1);	//atoi is ascii to integer conversion

                if(ptr == NULL)
                {
                    printf("Error : Memory allocation failure\n");
                    continue;
                }
                
                ret = ReadFile(fd,ptr,atoi(command[2]));
                if(ret == -1)
                    printf("ERROR : File not existing\n");
              
                if(ret == -2)
                    printf("ERROR : Permission denied\n");

                if(ret == -3)
                    printf("ERROR : Reached at end of file\n");

                if(ret == -4)
                    printf("ERROR : It is not regular file\n");

                if(ret == 0)
                    printf("ERROR : File empty\n");

                if(ret > 0)
                {
                    write(2,ptr,ret);				//printf call internally systemcall//write() is system call//write(2 as moniter,ptr is from where,ret is how)//write is like printf
                }
                continue;
          }
          else
          {
              printf("\nERROR : Command not found !!!\n");
              continue;
          }
      }
      else if(count == 4)
      {
          if(strcmp(command[0],"lseek") == 0)
          {
              fd = GetFDFromName(command[1]);
              if(fd == -1)
              { 
                  printf("Error : Incorrect parameter\n");
                  continue; 
              }
          
              ret = LseekFile(fd,atoi(command[2]),atoi(command[3]));
              if(ret == -1)
              {

                  printf("ERROR : Unable to perform lseek\n");
              }
          }
          else
          {
               printf("\nERROR : Command not found !!!\n");
               continue;
          }
      }
      else
      {
           printf("\nERROR : Command not found !!!\n");
           continue;
      }
   } 
   return 0;
}
