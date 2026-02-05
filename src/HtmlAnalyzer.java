import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Deque;
import java.util.ArrayDeque;

public class HtmlAnalyzer {
    public static void main(String[] args) {
            if(args.length!=1)
                return;

            try {
                URL url = new URL(args[0]);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

                Deque<String> stack = new ArrayDeque<>();
                String deepestText = null;
                int maxDepth = -1;

                String line;

                while((line = reader.readLine()) != null){
                    line = line.trim();

                    if(line.isEmpty()) continue;

                    // Encontrou uma tag de abertura.
                    if(line.matches("<[a-zA-Z]+>")){
                        String tag = line.substring(1, line.length()-1);
                        stack.push(tag);
                    }

                    // Encontrou uma tag de fechamento.
                    else if(line.matches("</[a-zA-Z]+>")){
                        String tag = line.substring(2, line.length()-1);

                        if(stack.isEmpty() || !stack.peek().equals(tag)){
                            System.out.println("malformed HTML");
                            return;
                        }

                        stack.pop();
                    }
                    // E por fim, se encontrar um texto.
                    else{
                        int depth = stack.size();
                        // Esse teste garante que o texto mais profundo será escolhido e que seja
                        // o primeiro no caso de textos com profundidade igual.
                        if(depth > maxDepth){
                            maxDepth = depth;
                            deepestText = line;
                        }
                    }

                }
                // Se após ler o HTML ainda houver alguma tag na pilha então estava malformado.
                if(!stack.isEmpty()){
                    System.out.println("malformed HTML");
                    return;
                }

                if(deepestText != null){
                    System.out.println(deepestText);
                }

            } catch(Exception e) {
                System.out.println("URL connection error");
            }
        }
    }