#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#define MAX 2000
typedef struct{
    char * name; //dynamic string
    int rank;
}player_t;

//function prototype(s)
player_t* scanRoster(player_t *roster);
void merge(player_t *roster, int p, int q, int r);
void mergeSort(player_t * roster, int p, int r);
void printRoster(player_t *roster);

    int main(void)
{
    int seed;
    printf("Enter seed: ");
    scanf("%d", &seed);
    srand(seed);
    player_t *roster = (player_t*) malloc(sizeof(player_t) * MAX);
    player_t *team1 = (player_t*) malloc(sizeof(player_t) * MAX / 2);
    player_t *team2 = (player_t*) malloc(sizeof(player_t) * MAX / 2);
    roster = scanRoster(roster);

    printRoster(roster);

    mergeSort(roster, 0,  MAX-1);

    double average1 = 0;
    double average2 = 0;
    //printf("Team 1 Rank Average is: %f\n", average1);
    //printf("Team 2 Rank Average is: %f\n", average2);

    printf("After Merge Sort\n");
	printRoster(roster);
    printf("\n");
    printf("------------------------------------------------------------------------\n");
    printf("------------------------------------------------------------------------\n");
    return 0;
}

player_t* scanRoster(player_t *roster)
{
    FILE *fptr = fopen("players.txt", "r");
    char name[20];
    int index = 0;
    while(fscanf(fptr, "%s", name) == 1)
    {
        roster[index].name = (char *) malloc(sizeof(char) * 20);
        strcpy(roster[index].name, name);
        roster[index].rank = rand() % 5 + 1;
        ++index;
    }
    fclose(fptr);
    return roster;
}

void merge(player_t *roster, int p, int q, int r)
{
    int n1 = q - p + 1;
    int n2 = r - q;
    player_t * leftarr = (player_t *) malloc(sizeof(player_t) * MAX);
    player_t * rightarr = (player_t *) malloc(sizeof(player_t) * MAX);

    for(int x = 0; x < n1; ++x) {
        leftarr[x] = roster[p + x];
    }

    for(int x = 0; x < n2; ++x){
        rightarr[x] = roster[q + x + 1];
    }

    int i = 0;
    int j = 0;
    int k = p;

    //merge
    while (i < n1 && j < n2)
    {
        if (leftarr[i].rank <= rightarr[j].rank)
        {
            roster[k]= leftarr[i];
            i++;
        }
        else
        {
            roster[k]= leftarr[j];
            j++;
        }

        k++;
    }
    //copy the remaining elements once out of bounds
    while (i < n1)
    {
        roster[k] = leftarr[i];
        i++;
        k++;
    }
    while (j < n2)
    {
        roster[k] = rightarr[j];
        j++;
        k++;
    }
    free(leftarr);
    free(rightarr);
}

void mergeSort(player_t * roster, int p, int r)
{
    if(p < r)
    {
        int q = (r + p) / 2;
        mergeSort(roster, p, q);
        mergeSort(roster, q + 1, r);
        merge(roster, p, q, r);
    }
}

void printRoster(player_t *roster) {
    for (int i = 0; i < MAX; i++)
    {
        printf("Player name:%s\nPlayer rank:%d\n---------- %d\n", roster[i].name, roster[i].rank, i);
    }
    printf("||||||||||||||\n");
}
