package org.example.miniforum.controller;

import lombok.RequiredArgsConstructor;
import org.example.miniforum.domain.Board;
import org.example.miniforum.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor // lombok: final fields ard injected by constructor
public class BoardController {
    private final BoardService boardService;

    // 글 목록
    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(defaultValue= "1") int page,
                       @RequestParam(defaultValue="5") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Board> boardPage = boardService.findAllBoards(pageable);
        for(Board board : boardPage) {
            System.out.println(board.getId());
        }
        System.out.println("---------------------");
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("currentPage", page);
        return "list";
    }


    // 글 상세 조회
    @GetMapping("/view")
    public String view(Model model, @RequestParam long id) {
        Board board = boardService.findBoardById(id);
        model.addAttribute("board", board);
        return "view";
    }

    // 게시글 등록
    @GetMapping("/writeform")
    public String writeForm(Model model) {
        model.addAttribute("board", new Board());
        return "writeform";
    }

    @PostMapping("/writeform")
    public String write(@ModelAttribute Board board,
                        RedirectAttributes redirectAttributes) {
        boardService.saveBoard(board);
        redirectAttributes.addFlashAttribute("message", "새로운 게시글이 등록되었습니다.");
        return "redirect:/list";
    }


}
