package py.edu.facitec.pedidos.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.SubscriptionExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CarsExceptionSubscritionResolver {
	public class CarsExceptionSubscriptionResolver extends SubscriptionExceptionResolverAdapter {

		@Override
		protected GraphQLError resolveToSingleError(Throwable exception) {
			// TODO Auto-generated method stub
			return new GraphQLError() {
				@Override
				public String getMessage() {
					log.error("Message: {}", exception.getMessage());
					return exception.getMessage();
				}

				@Override
				public List<SourceLocation> getLocations() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public ErrorClassification getErrorType() {
					// TODO Auto-generated method stub
					return null;
				}
			};
		}
		
		
	}
}
