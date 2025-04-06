<<<<<<< HEAD

# Ufit

StartUp Backend App Untuk Fitnes / GYM


## API Reference

#### Login

```http
  POST localhost:8085/service/login
```

| Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | Username |
| `password` | `string` | Password |

#### Logout

```http
  POST localhost:8085/service/Logout
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `username`      | `string` | username |

#### Register Account

```http
  POST localhost:8085/service/register
```

| Body | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `username`      | `string` | username |
| `password`      | `string` | password |
| `nama_depan`      | `string` | nama depan |
| `nama_belakang`      | `string` | nama belakang |
| `gender`      | `string` | gender |
| `birthdate`      | `date` | tanggal lahir |
| `birthplace`      | `string` | tempat lahir |
| `phone`      | `string` | no tlp |
| `email`      | `string` | email |

#### Get All Account

```http
  GET localhost:8085/service/getall_account
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |


#### Get Single Account

```http
  GET localhost:8085/service/account/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Integer` | id |



## Optimizations

1. Mencegah Multiple Login
2. Logout Refresh Status Login
3. Perbaikan Posisi FIle Dalam Folder



## Used By

Project ini merupakan home lab pribadi dan akan diperutukan oleh Company setelah MVP:

- UCTECH
- CHEN CORP

=======
uFit <br>
![image](https://github.com/user-attachments/assets/72f07489-a229-4571-9015-4213b1af9d14)
<br>
Login -> OK : localhost/service/login -- 05/04/2025<br> 
Register -> OK : localhost/service/register -- 05/04/2025<br>
Logout -> OK : localhost/service/register -- 05/04/2025<br>
Get ALL Account -> OK : localhost/service/getall_account -- 06/04/2025<br>
Get Sigle Account -> OK : localhost/service/account/{id} -- 06/04/2025<br>
<br>
sisanya comming soon. bertahap<br>

<br>
Change Log <br>
<hr>
1. Mencegah Multiple Login -- 06/04/2025 <br>
2. Logout Refresh Active Login Status -- 06/04/2025 <br>
3. Perbaikan Direktory Service -- 06/04/2025 <br>
>>>>>>> e01557d5fdd19fd0af3eb6f00099fd29e0121aa0
