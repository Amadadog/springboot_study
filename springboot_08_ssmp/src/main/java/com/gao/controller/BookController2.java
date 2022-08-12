package com.gao.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gao.controller.utils.R;
import com.gao.domain.Book;
import com.gao.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController2 {
    @Autowired
    private IBookService IBookService;

    @GetMapping
    public R getAll() {
        return new R(true,IBookService.list());
    }

    @PostMapping
    public R save(@RequestBody Book book) throws IOException {
        if (book.getName().equals("123"))throw new IOException();
        boolean flag = IBookService.save(book);
        return new R(flag,flag?"添加成功^_^":"添加失败-_-!");
    }

    @PutMapping
    public R update(@RequestBody Book book) {
        return new R(true,IBookService.updateById(book));
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        return new R(true,IBookService.removeById(id));
    }

    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id) {
        return new R(true,IBookService.getById(id));
    }

    @GetMapping("{currentPage}/{pageSize}")
    public R getPage(@PathVariable int currentPage, @PathVariable int pageSize,Book book) {
        IPage<Book> page = IBookService.getPage(currentPage, pageSize,book);
        //如果当前页码值大于总页码值，那么重新执行查询操作。使用最大页码值作为当前页码
        if( currentPage > page.getPages()) {
            page = IBookService.getPage((int) page.getPages(), pageSize,book);
        }
        return new R(null != page ,page);
    }
}
