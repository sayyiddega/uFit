
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

