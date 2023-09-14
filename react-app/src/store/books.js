const POST_BOOK = "Books/POST_BOOK";
const EDIT_BOOK = "Books/EDIT_BOOK";
const GET_BOOK = "Books/GET_BOOK";
const DELETE_BOOK = "Books/DELETE_BOOK"

const postBook = (Book) => ({
  type: POST_BOOK,
  payload: Book,
});

const editBook = (Book) => ({
  type: EDIT_BOOK,
  payload: Book
});

const getBooks = (Books) => ({
  type: GET_BOOK,
  payload: Books,
});

const deleteBook = (id) => ({
  type: DELETE_BOOK,
  payload: id
});

export const fetchAllBooks = () => async (dispatch) => {
  const response = await fetch("/api/Books");
  if (response.ok) {
    const Books = await response.json();
    dispatch(getBooks(Books));
    return Books;
  }
};

export const fetchPostBook = (Book) => async (dispatch) => {
  const { caption, url } = Book;
  const response = await fetch("/api/Books", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(Book),
  });
  if (response.ok) {
    const Book = await response.json();
    dispatch(postBook(Book));
    return response;
  }
};

export const fetchEditBook = (BookId, payload) => async (dispatch) => {
  console.log(BookId)
  // const formData = new FormData();
  // formData.append("title", newTitle);
  // formData.append("body", newBody);
  const res = await fetch(`/api/Books/${BookId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(payload)
  });
  if (res.ok) {
    const data = await res.json()
    dispatch(editBook(data))
    return data
  }
}

export const fetchDeleteBook = (id) => async (dispatch) => {
  const response = await fetch(`/api/Books/${id}`, {
    method: "DELETE",
  });
  console.log(response)
  if (response.ok) {
    dispatch(deleteBook(id))
    return response
  }
}

const initialState = {};

export default function reducer(state = initialState, action) {
  let newState;
  switch (action.type) {
    case GET_BOOK:
      newState = action.payload;
      return newState;
    case POST_BOOK:
      newState = Object.assign({}, state);
      newState[action.payload.id] = action.payload;
      return newState;
    case EDIT_BOOK:
      newState = Object.assign({}, state);
      newState[action.payload.id] = action.payload;
      return newState;
    case DELETE_BOOK:
      newState = Object.assign({}, state);
      delete newState[action.payload.id];
      return newState;
    default:
      return state;
  }
}
