package com.java456.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name="t_book_borrow")
public class BookBorrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="userId")
    private User uId; // 用户Id

    @Column(length=20)
    private String userName; // 用户名称

    @Column(length=20)
    private String userPhone; // 联系方式

    @Column(length=50)
    private Integer bookId; //图书名称

    @Column(length=50)
    private String bookName; //图书名称

    @Column(length=50)
    private String bookAuthor; //图书作者

    @Column(length=200)
    private String bookPress; //出版社

    @Column(length=50)
    private String bookBianhao; //图书编号

    @Column(length=200)
    private String bookLocation; //存放位置

    @Column(columnDefinition = "text")
    private String Introduce; //简介



    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowCreateDateTime;//借阅时间

    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowLastDateTime;//借阅期限

    @Column(length=200)
    private String bookImageUrl;//图书封面

    @Column(length = 10, columnDefinition = "int default 1")
    private Integer num;//图书借阅数量

    @Column(length=50)
    private String bookType; // 图书类型

    @NotNull(message = "借阅状态 0申请 1归还 2续借 3预借")
    @Column(length = 10)
    private Integer state;//借阅状态 0申请 1归还 2续借 3预借


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getuId() {
        return uId;
    }

    public void setuId(User uId) {
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPress() {
        return bookPress;
    }

    public void setBookPress(String bookPress) {
        this.bookPress = bookPress;
    }

    public String getBookBianhao() {
        return bookBianhao;
    }

    public void setBookBianhao(String bookBianhao) {
        this.bookBianhao = bookBianhao;
    }

    public String getBookLocation() {
        return bookLocation;
    }

    public void setBookLocation(String bookLocation) {
        this.bookLocation = bookLocation;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getBorrowCreateDateTime() {
        return borrowCreateDateTime;
    }

    public void setBorrowCreateDateTime(Date borrowCreateDateTime) {
        this.borrowCreateDateTime = borrowCreateDateTime;
    }
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getBorrowLastDateTime() {
        return borrowLastDateTime;
    }

    public void setBorrowLastDateTime(Date borrowLastDateTime) {
        this.borrowLastDateTime = borrowLastDateTime;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BookBorrow() {

    }



    public BookBorrow(User user,Book book,Date borrowCreateDateTime,Date borrowLastDateTime,Integer state) {
        this.uId = user;
        this.userName = user.getName();
        this.userPhone = user.getPhone();
        this.bookId = book.getId();
        this.bookName = book.getName();
        this.bookAuthor = book.getAuthor();
        this.bookPress = book.getPress();
        this.bookBianhao = book.getBianhao();
        this.bookLocation = book.getBookLocation();
        Introduce = book.getIntroduce();
        this.borrowCreateDateTime = borrowCreateDateTime;
        this.borrowLastDateTime = borrowLastDateTime;
        this.bookImageUrl = book.getImageUrl();
        this.num = 1;
        this.bookType = book.getBookType().getName();
        this.state = state;
    }

    @Override
    public String toString() {
        return "BookBorrowService{" +
                "id=" + id +
                ", userId=" + uId +
                ", userName=" + userName +
                ", userPhone=" + userPhone +
                ", bookName=" + bookName +
                ", bookAuthor=" + bookAuthor +
                ", bookPress=" + bookPress +
                ", bookBianhao=" + bookBianhao +
                ", bookLocation=" + bookLocation +
                ", Introduce=" + Introduce +
                ", borrowCreateDateTime=" + borrowCreateDateTime +
                ", borrowLastDateTime=" + borrowLastDateTime +
                ", bookImageUrl=" + bookImageUrl +
                ", num=" + num +
                ", bookType=" + bookType +
                ", state=" + state +
                '}';
    }
}








