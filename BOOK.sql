commit;

drop sequence seq_board;
drop table tbl_board;

create sequence seq_board;

create table tbl_board(
    bno number(10,0),
    title varchar2(200) not null,
    content varchar2(2000) not null,
    writer varchar2(50) not null,
    regdate date default sysdate,
    updatedate date default sysdate
);

alter table tbl_board add constraint pk_board primary key (bno);

--댓글 테이블
drop sequence seq_reply;
drop table tbl_reply;

create table tbl_reply(
    rno number(10,0),
    bno number(10,0) not null,
    reply varchar2(1000) not null,
    replyer varchar2(50) not null,
    replyDate date default sysdate,
    updateDate date default sysdate
);

create sequence seq_reply;

alter table tbl_reply add constraint pk_reply primary key(rno);

alter table tbl_reply add constraint fk_reply_board
foreign key (bno) references tbl_board (bno);
    

--더미 데이터 추가
insert into tbl_board(bno, title, content, writer) values (seq_board.nextval, '테스트 제목', '테스트 내용', 'user00');

select * from tbl_board where bno > 0;
commit;

--재귀복사로 데이터 개수 늘리기
insert into tbl_board (bno, title, content, writer)
(select seq_board.nextval, title, content, writer from tbl_board);
--대용량 데이터 select시 매우 많은 시간이 걸림.
select * from tbl_board order by bno desc;

--인덱스를 이용한 정렬
select /*+ INDEX_DESC(tbl_board pk_board) */
*
from
tbl_board
where bno > 0;


select * from tbl_board order by bno desc;

select /*+ INDEX_DESC (tbl_board pk_board) */*
from tbl_board;
--full 힌트
select /*+ FULL(tbl_board) */ * from tbl_board order by bno desc;

--인덱스를 이용한 접근 시 rownum

select /*+ INDEX_ASC(tble_board pk_board) */
rownum rn, bno, title, content
from tbl_board;

--
select /*+ FULL(tbl_board) */
rownum rn, bno, title
from tbl_board where bno > 0 order by bno;
--한페이지 당 10개 데이터 출력
select /*+INDEX_DESC(tbl_board pk_board) */
rownum rn, bno, title, content
from 
tbl_board
where rownum <=10;
--2페이지: 아무 결과가 나오지 않는다. 잘못된 구문
select /*+INDEX_DESC(tbl_board pk_board) */
rownum rn, bno, title, content
from 
tbl_board
where rownum > 10 and rownum <= 20;
--올바른 구문: rownum은 반드시 1이 포함되도록 해야 한다.
select /*+INDEX_DESC(tbl_board pk_board) */
rownum rn, bno, title, content
from 
tbl_board
where rownum <= 20;

--인라인뷰 적용
select 
bno, title, content
from(
select /*+INDEX_DESC(tbl_board pk_board) */
rownum rn, bno, title, content
from 
tbl_board
where rownum <= 20
) where rn > 10;

--검색처리(단일 항묵)
select * from(
	select /*+INDEX_DESC(tbl_board pk board) */
		rownum rn, bno, title, content, writer, regdate, updatedate
	from tbl_board
	where
	--변경 부분
	title like '%테스트%'
	and rownum <= 20
)
where rn > 10;
--검색 처리(다중항목)
select * from(
	select /*+INDEX_DESC(tbl_board pk board) */
		rownum rn, bno, title, content, writer, regdate, updatedate
	from tbl_board
	where
	--변경 부분
	(title like '%테스트%' or content like '%테스트%')
	and rownum <= 20
)
where rn > 10;

select * from tbl_board where rownum <10 order by bno desc;
select * from tbl_reply order by rno desc;


--댓글 페이징 처리(인덱스 이용)
--특정 게시물의 rno 순번대로 댓글 데이터 조회
create index idx_reply on tbl_reply(bno desc, rno asc);

select /*+INDEX(tbl_reply idx_reply) */ 
    rownum rn, bno, rno, reply, replyer, replyDate, updatedate
    from tbl_reply
    where bno = 4194302
    and rno > 0;
    
--10개씩 2페이지 가지고 오기
select rno, bno, reply, replyer, replydate, updatedate
from(
    select /*+INDEX(tbl_reply idx_reply) */
        rownum rn, bno, rno, reply, replyer, replyDate, updatedate
    from tbl_reply
    where bno = 4194304 and rno > 0 and rownum <=20 
    )where rn > 10;